package com.olch.library.presentation.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Ольга on 16.12.2017.
 */
public abstract class AbstractView extends VerticalLayout implements View {

    private static final String ADD = "Add";
    private static final String EDIT = "Edit";
    private static final String DELETE = "Delete";

    protected final Grid grid = new Grid();
    protected final HorizontalLayout actions = new HorizontalLayout();
    protected final HorizontalLayout navigationMenu;

    private final Button addAction = new Button(ADD);
    private final Button editAction = new Button(EDIT);
    private final Button deleteAction = new Button(DELETE);

    public AbstractView(HorizontalLayout navigationMenu) {
        this.navigationMenu = navigationMenu;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        initLayout();
        initListeners();
    }

    public abstract void refresh();

    protected abstract void add();

    protected abstract void edit();

    protected abstract void delete();

    private void initLayout() {
        actions.setSpacing(true);
        actions.setMargin(new MarginInfo(false, false, true, false));
        actions.addComponents(addAction, editAction, deleteAction);
    }

    private void initListeners() {
        addAction.addClickListener(e -> add());
        editAction.addClickListener(e -> edit());
        deleteAction.addClickListener(e -> delete());
    }

}
