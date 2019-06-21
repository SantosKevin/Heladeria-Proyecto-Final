
package aplicacion.controladores.beans.forms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.ChartSeries;

/**
 * managedbean que permite trabajar con los graficos estadisticos
 * @author Falcao
 */
@ManagedBean
@ViewScoped
public class GraficosFormBean implements Serializable{
   
    /**
     * Creates a new instance of GraficosFormBean
     */
    public GraficosFormBean() {
    }
    
}
