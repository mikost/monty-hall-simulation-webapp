package name.mikkoostlund.montyweb.ui.wicket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import name.mikkoostlund.montyweb.domain.montyhallsimulation.ContestantResult;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.ShowContestant;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.SimulationResult;
import name.mikkoostlund.montyweb.service.MontyService;
import name.mikkoostlund.montyweb.service.TableSimulationResult;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.RangeValidator;

public class MontySim extends MontyTemplate {

	@Inject
	MontyService service;

	private Integer numberOfRuns = null;
	private Integer numberOfDoors = null;
	private final List<String> contestantSelections = new ArrayList<>();

	private DefaultDataTable<TableSimulationResult, String> table;

	public MontySim(PageParameters pageParameters) {
		super(pageParameters);

		Form<MontySim> form;
		this.add(form = new Form<MontySim>("simForm",
				new CompoundPropertyModel<MontySim>(this)) {

			@Override
			protected void onSubmit() {
				List<ShowContestant> allShowContestants = service
						.getContestantTypes();
				Set<ShowContestant> showContestantsInShow = new HashSet<>();

				for (String selection : contestantSelections) {
					for (ShowContestant showContestant : allShowContestants) {
						if (showContestant.getDescription().equals(selection)) {
							showContestantsInShow.add(showContestant);
						}
					}
				}

				SimulationResult simulationResult = service.runSimulation(
						numberOfRuns, numberOfDoors, showContestantsInShow);
				this.info("The simulation was run "
						+ simulationResult.getNumberOfRuns() + " times.");
				for (ContestantResult cr : simulationResult
						.getContestantResults()) {
					this.info("Using the \""
							+ cr.getShowContestant().getDescription()
							+ "\", the contestant won " + cr.getTimesWon()
							+ " times.");
				}
			}
		});

		TextField<Integer> numberOfRunsTextField;
		form.add(numberOfRunsTextField = new TextField<Integer>("numberOfRuns"));
		numberOfRunsTextField.setRequired(true);
		numberOfRunsTextField.add(new RangeValidator<>(1, null));
		numberOfRunsTextField.setLabel(new ResourceModel(
				"label.number.of.shows"));

		TextField<Integer> numberOfDoorsTextField;
		form.add(numberOfDoorsTextField = new TextField<Integer>(
				"numberOfDoors"));
		numberOfDoorsTextField.setRequired(true);
		numberOfDoorsTextField.add(new RangeValidator<Integer>(3, null));
		numberOfDoorsTextField.setLabel(new ResourceModel(
				"label.number.of.doors"));

		CheckGroup<String> contestantSelectionsCheckGroup;
		form.add(contestantSelectionsCheckGroup = new CheckGroup<>(
				"contestantSelections"));
		contestantSelectionsCheckGroup
				.add(new IValidator<Collection<String>>() {
					@Override
					public void validate(
							IValidatable<Collection<String>> validatable) {
						Collection<String> selections = validatable.getValue();
						if (selections.isEmpty()) {
							validatable.error(new ValidationError()
									.addKey("AtLeastOneContestantMustBeIncluded"));
						}
					}
				});

		List<String> contestantOptions = new ArrayList<>();
		for (ShowContestant sc : service.getContestantTypes()) {
			contestantOptions.add(sc.getDescription());
		}

		ListView<String> checkboxListView;
		checkboxListView = new ListView<String>("contestantOptions",
				contestantOptions) {
			@Override
			protected void populateItem(ListItem<String> item) {
				item.add(new Check<String>("selected", item.getModel()));
				item.add(new Label("description", item.getModel()));
			}

			private static final long serialVersionUID = 1L;
		};
		checkboxListView.setReuseItems(true);

		contestantSelectionsCheckGroup.add(checkboxListView);

		this.add(new FeedbackPanel("feedback"));

		List<PropertyColumn<TableSimulationResult, String>> columns = new ArrayList<>();
		columns.add(new PropertyColumn<TableSimulationResult, String>(
				new Model<>("Time"), "time", "time"));
		columns.add(new PropertyColumn<TableSimulationResult, String>(
				new Model<>("NumberOfRuns"), "numberOfRuns", "numberOfRuns"));
		columns.add(new PropertyColumn<TableSimulationResult, String>(
				new Model<>("NumberOfDoors"), "numberOfDoors", "numberOfDoors"));
		columns.add(new PropertyColumn<TableSimulationResult, String>(
				new Model<>("Switch wins"), "switchingWins", "switchingWins"));
		columns.add(new PropertyColumn<TableSimulationResult, String>(
				new Model<>("Keep wins"), "keepingWins", "keepingWins"));
		ISortableDataProvider<TableSimulationResult, String> resultProvider = new ResultProvider();
		table = new DefaultDataTable<TableSimulationResult, String>(
				"datatable", columns, resultProvider, 10);
		add(table);
	}

	@Override
	protected void onConfigure() {
		super.onConfigure();
		table.setVisibilityAllowed(MontyWebSession.get().getRoles()
				.contains("uzers"));
	}

	class ResultProvider extends
			SortableDataProvider<TableSimulationResult, String> {
		int callCounter = 0;

		private class SortableDataProviderComparator implements
				Comparator<TableSimulationResult>, Serializable {

			@SuppressWarnings({ "unchecked", "rawtypes" })
			public int compare(final TableSimulationResult o1,
					final TableSimulationResult o2) {
				final String property = getSort().getProperty();
				if (property == null) {
					return -1;
				}
				PropertyModel<Comparable> model1 = new PropertyModel<Comparable>(
						o1, property);
				PropertyModel<Comparable> model2 = new PropertyModel<Comparable>(
						o2, property);
				Comparable value1 = model1.getObject();
				Comparable value2 = model2.getObject();
				if (value1 == null) {
					return 1;
				}
				if (value2 == null) {
					return -1;
				}
				int result = value1.compareTo(value2);

				if (!getSort().isAscending()) {
					result = -result;
				}

				return result;
			}
		}

		@Override
		public Iterator<? extends TableSimulationResult> iterator(long first,
				long count) {
			System.out.println("*** " + (++callCounter) + ": (" + first + "->"
					+ (first + count) + ") " + stringMe());
			List<TableSimulationResult> simulationResults = service
					.getSimulationResults();

			if (getSort() != null) {
				Collections.sort(simulationResults,
						new SortableDataProviderComparator());
			}
			return simulationResults
					.subList((int) first, (int) (first + count)).iterator();
		}

		private String stringMe() {
			return new StringBuffer().append("(").append("sortState=")
					.append(this.getSortState()).append(")").toString();
		}

		@Override
		public long size() {
			return service.getSimulationResults().size();
		}

		@Override
		public IModel<TableSimulationResult> model(
				TableSimulationResult simulationResult) {
			return new LoadableDetachableModel<TableSimulationResult>(
					simulationResult) {

				@Override
				protected TableSimulationResult load() {
					return null;
				}
			};
		}
	}

}
