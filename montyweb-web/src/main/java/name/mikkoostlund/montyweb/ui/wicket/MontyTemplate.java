package name.mikkoostlund.montyweb.ui.wicket;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.wicket.ClassAttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class MontyTemplate extends WebPage {
    private static final long serialVersionUID = 1L;
    private List<Locale> choices = Arrays.asList(Locale.ENGLISH, new Locale("sv"));
    private IModel<Locale> model = new Model<Locale>();

    public MontyTemplate(PageParameters pageParameters) {
        super(pageParameters);

        DropDownChoice<Locale> language;
        add(language = new DropDownChoice<Locale>("language", model, choices) {
            @Override
            protected String getNullKeyDisplayValue() {
                return "Choose language:";
            }
        });
        language.add(new AjaxFormComponentUpdatingBehavior("change") {
            
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                Session.get().setLocale(model.getObject());
                target.add(MontyTemplate.this);
            }
        });
        language.setChoiceRenderer(new IChoiceRenderer<Locale>() {
            @Override
            public Object getDisplayValue(Locale locale) {
                return locale.getDisplayLanguage(locale);
            }

            @Override
            public String getIdValue(Locale object, int index) {
                return "" + index;
            }

            @Override
            public Locale getObject(String id, IModel<? extends List<? extends Locale>> choices) {
                return choices.getObject().get(Integer.parseInt(id));
            }
        });

        add(new SignInWidget("signin-widget"));

        List<Link> linkList = Arrays.asList(
                Link.of(new ResourceModel("label.introduction"), MontyMain.class),
                Link.of(new ResourceModel("label.simulation"),   MontySim.class)
            );

        add(new ListView<Link>("tabs", linkList) {
            @Override
            protected void populateItem(ListItem<Link> item) {
                Link link = item.getModelObject();
                item.add(new BookmarkablePageLink<Void>("link", link.pageClass)
                    .setBody(link.linkText));
                if (link.pageClass == MontyTemplate.this.getClass()) {
                    item.add(new ClassAttributeModifier() {
                        @Override
                        protected Set<String> update(Set<String> oldClasses) {
                            oldClasses.add("active");
                            return oldClasses;
                        }
                    });
                }
            }
        });
    }

    private static class Link implements Serializable {
        private IModel<String> linkText;
        private Class<? extends Page> pageClass;

        public Link(IModel<String> linkText, Class<? extends Page> page) {
            this.linkText = linkText;
            this.pageClass = page;
        }
        
        static Link of(IModel<String> linkText, Class<? extends Page> page) {
            return new Link(linkText, page);
        }
    }
}
