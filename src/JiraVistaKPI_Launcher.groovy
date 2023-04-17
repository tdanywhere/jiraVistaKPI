import groovy.lang.GroovyShell;
/**
 * Just a workaround class to get a "Launch Configuration" created.
 * Create this Launch Configuration by right clicking on the class file (Project Explorer),
 * Run As... - Run Configurations - Java Application.
 * 
 * This Run Configuration must then be used for "export" as runnable jar.
 *   
 * @author Thomas Deter
 *
 */
public class JiraVistaKPI_Launcher {
    public static void main(String[] args) {
        GroovyShell.main(args);
    }

}
