package org.nocket.gen.page.visitor.bind.builder.components;

import gengui.domain.DomainObjectReference;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.convert.IConverter;
import org.nocket.component.table.ColumnSortOrder;
import org.nocket.component.table.GeneratedGenericDataTableColumnConfigurator;
import org.nocket.component.table.GenericDataTableColumnConfigurator;
import org.nocket.component.table.GenericDataTableConfigurator;
import org.nocket.component.table.GenericDataTablePanel;
import org.nocket.component.table.TableItemPosition;
import org.nocket.component.table.TableSortType;
import org.nocket.gen.domain.element.MultivalueColumnElement;
import org.nocket.gen.domain.visitor.html.styling.StylingFactory;
import org.nocket.gen.domain.visitor.html.styling.common.TablePanelBuilderI;
import org.nocket.gen.page.DMDWebGenPageContext;
import org.nocket.gen.page.element.FormElement;
import org.nocket.gen.page.element.TableElement;
import org.nocket.gen.page.element.synchronizer.SynchronizerHelper;
import org.nocket.gen.page.element.synchronizer.TableButtonCallback;
import org.nocket.gen.page.element.synchronizer.TableDownloadCallback;

public class GeneratedGenericDataTableFactory {

    protected TableElement e;
    protected String[] overrideSortableColumns;
    protected final GenericDataTableConfigurator<?> config;

    public GeneratedGenericDataTableFactory(TableElement e) {
        this.e = e;
        this.config = new GenericDataTableConfigurator();
        new TableExampleStructureAdopter(e).populate(config);
        new TableAttributeAdopter(e).populate(config);
    }

    public GeneratedGenericDataTableFactory(TableElement e, GenericDataTableConfigurator config) {
        this.e = e;
        this.config = config;
    }

    public GenericDataTablePanel<?> createTable() {
        IModel<List<?>> data = e.getModel();
        DMDWebGenPageContext context = e.getContext();
        config.withColumnConfigurator(createColumnConfigurator());
        GenericDataTablePanel<?> table = createTable(e.getWicketId(), data, config);
        addCellContentConverters(table);
        return table;
    }

    protected void addCellContentConverters(GenericDataTablePanel table) {
        for (MultivalueColumnElement<DomainObjectReference> col : e.getDomainElement().getColumns()) {
            addColumnContentConverter(table, col);
        }
    }

    protected void addColumnContentConverter(GenericDataTablePanel table,
            MultivalueColumnElement<DomainObjectReference> col) {
        String format = new SynchronizerHelper(e.getContext(), col).getFormat(col.getMethod());
        Class<?> returnType = col.getMethod().getReturnType();
        IConverter<?> converter = createColumnConverter(col, format, returnType);
        if (converter != null) {
            table.setConverterForColumn(returnType, col.getColumnName(), converter);
        }
    }

    protected IConverter<?> createColumnConverter(MultivalueColumnElement<DomainObjectReference> col, String format,
            Class<?> returnType) {
        if (col.isDateType()) {
            return createDateConverter(format, returnType);
        } else if (col.isNumberType()) {
            return createNumberConverter(format, returnType);
        } else if (returnType.isEnum()) {
            return createEnumConverter(format, returnType);
        }
        return createOtherConverter(format, returnType);
    }

    protected IConverter<?> createOtherConverter(String format, Class<?> returnType) {
        return null;
    }

    protected IConverter<?> createEnumConverter(String format, Class<?> returnType) {
        return new EnumConverter(e.getContext());
    }

    protected IConverter<?> createDateConverter(String format, Class<?> returnType) {
        return (format != null) ? new SimpleDateConverter(format) : null;
    }

    protected IConverter<?> createNumberConverter(String format, Class<?> returnType) {
        if (format != null) {
            IConverter<?> converter = WebApplication.get().getConverterLocator().getConverter(returnType);
            GeneratedNumberTextField.customizeConverter(converter, format);
            return converter;
        }
        return null;
    }

    public static GenericDataTablePanel createTable(String wicketId,
            IModel<List<?>> data, GenericDataTableConfigurator<?> config) {
        TablePanelBuilderI builder = StylingFactory.getStylingStrategy().getTablePanelBuilder();
        builder.initTablePanelBuilder(wicketId, data, config);
        return builder.getTablePanel();
    }

    protected List<String> createSortableColumns() {
        if (overrideSortableColumns != null)
            return Arrays.asList(overrideSortableColumns);
        return e.getDomainElement().getPropertyColumnNames();
    }

    protected GenericDataTableColumnConfigurator createColumnConfigurator() {
        GenericDataTableColumnConfigurator columnConfigurator = config.getColumnConfigurator();
        if (columnConfigurator == null) {
            List<String> columns = e.getDomainElement().getPropertyColumnNames();
            List<String> sortableColumns = createSortableColumns();
            List<TableDownloadCallback> downloadColumns = e.getDownloadCallbacks();
            List<TableButtonCallback> tableButtons = e.getButtonCallbacks();
            Form<?> form = (Form<?>) e.getContext().getComponentRegistry().getComponent(FormElement.DEFAULT_WICKET_ID);
            columnConfigurator = new GeneratedGenericDataTableColumnConfigurator(columns, sortableColumns,
                    downloadColumns, tableButtons, form, e);
        }
        return columnConfigurator;
    }

    /************************************************************************************
     * C o n v e n i e n c e w r a p p e r m e t h o d s f o r t h e c o n f i g
     * u r a t o r //
     ************************************************************************************/

    public GenericDataTableConfigurator config() {
        return config;
    }

    public GeneratedGenericDataTableFactory withSortType(TableSortType sortType) {
        config.withSortType(sortType);
        return this;
    }

    public GeneratedGenericDataTableFactory withColumnConfigurator(GenericDataTableColumnConfigurator columnConfigurator) {
        config.withColumnConfigurator(columnConfigurator);
        return this;
    }

    public GeneratedGenericDataTableFactory withRowsPerPage(int rowsPerPage) {
        config.withRowsPerPage(rowsPerPage);
        return this;
    }

    public GeneratedGenericDataTableFactory withInitialSortOrder(ColumnSortOrder initialSortOrder) {
        config.withInitialSortOrder(initialSortOrder);
        return this;
    }

    public GeneratedGenericDataTableFactory withInitialSortColumn(String initialSortColumn) {
        config.withInitialSortColumn(initialSortColumn);
        return this;
    }

    public GeneratedGenericDataTableFactory withColumns(String... columns) {
        config.withColumns(columns);
        return this;
    }

    public GeneratedGenericDataTableFactory withSortColumns(String... sortColumns) {
        config.withSortColumns(sortColumns);
        return this;
    }

    public GeneratedGenericDataTableFactory withRowItemClass(Class rowItemClass) {
        config.withRowItemClass(rowItemClass);
        return this;
    }

    public GeneratedGenericDataTableFactory withNavigationBar(TableItemPosition navigationbarPosition) {
        config.withNavigationBar(navigationbarPosition);
        return this;
    }

}
