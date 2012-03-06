package name.cphillipson.experimental.gwt.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.DataGrid;



/**
 * Resource overrides for {@link DataGrid}.
 * @author cphillipson
 *
 */
//see http://groups.google.com/group/google-web-toolkit/browse_thread/thread/7592e4e4bc2728dd
public interface DataGridResource extends DataGrid.Resources {

    public static final DataGridResource INSTANCE = GWT.create(DataGridResource.class);

    public interface DataGridStyle extends DataGrid.Style {};

    @Override
    @Source({"datagrid-overrides.css"})
    DataGridStyle dataGridStyle();
}