package name.mikkoostlund.montyweb.ui.wicket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import name.mikkoostlund.montyweb.domain.montyhallsimulation.ContestantResult;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.ShowContestant;
import name.mikkoostlund.montyweb.domain.montyhallsimulation.SimulationResult;
import name.mikkoostlund.montyweb.service.MontyService;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
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

    public MontySim(PageParameters pageParameters) {
        super(pageParameters);

        Form<MontySim> form;
        this.add(form = new Form<MontySim>("simForm", new CompoundPropertyModel<MontySim>(this)) {
        	
            @Override
            protected void onSubmit() {
                List<ShowContestant> allShowContestants = service.getContestantTypes();
                Set<ShowContestant> showContestantsInShow = new HashSet<>();

                for (String selection : contestantSelections) {
                    for (ShowContestant showContestant : allShowContestants) {
                        if (showContestant.getDescription().equals(selection)) {
                            showContestantsInShow.add(showContestant);
                        }
                    }
                }

                SimulationResult simulationResult = service.runSimulation(numberOfRuns, numberOfDoors, showContestantsInShow);
                this.info("The simulation was run "+ simulationResult.getNumberOfRuns() +" times.");
                for (ContestantResult cr : simulationResult.getContestantResults()) {
                    this.info("Using the \""+ cr.getShowContestant().getDescription() +"\", the contestant won "+ cr.getTimesWon() +" times.");
                }
            }
        });

        TextField<Integer> numberOfRunsTextField;
        form.add(numberOfRunsTextField = new TextField<Integer>("numberOfRuns"));
        numberOfRunsTextField.setRequired(true);
        numberOfRunsTextField.add(new RangeValidator<>(1, null));
        numberOfRunsTextField.setLabel(new ResourceModel("label.number.of.shows"));

        TextField<Integer> numberOfDoorsTextField;
        form.add(numberOfDoorsTextField = new TextField<Integer>("numberOfDoors"));
        numberOfDoorsTextField.setRequired(true);
        numberOfDoorsTextField.add(new RangeValidator<Integer>(3, null));
        numberOfDoorsTextField.setLabel(new ResourceModel("label.number.of.doors"));
        
        CheckGroup<String> contestantSelectionsCheckGroup;
        form.add(contestantSelectionsCheckGroup = new CheckGroup<>("contestantSelections"));
        contestantSelectionsCheckGroup.add(new IValidator<Collection<String>>() {
            @Override
            public void validate(IValidatable<Collection<String>> validatable) {
                Collection<String> selections = validatable.getValue();
                if (selections.isEmpty()) {
                    validatable.error(new ValidationError().addKey("AtLeastOneContestantMustBeIncluded"));
                }
            }
        });

        List<String> contestantOptions = new ArrayList<>();
        for (ShowContestant sc : service.getContestantTypes()) {
        	contestantOptions.add(sc.getDescription());
        }

        ListView<String> checkboxListView;
        checkboxListView = new ListView<String>("contestantOptions", contestantOptions) {
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
    }
}
