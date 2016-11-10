package main;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.dbsync.CreateIfNoSchemaStrategy;
import org.apache.cayenne.access.dbsync.SchemaUpdateStrategy;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.configuration.server.ServerRuntimeBuilder;
import org.apache.cayenne.query.ObjectSelect;
import org.apache.cayenne.testdo.relationships_with_multiple_joins.Invoice;

public class Main {

	public static void main(String[] args) {
		ServerRuntimeBuilder builder = new ServerRuntimeBuilder();
		builder.addConfig( "cayenne-relationships-with-multiple-joins.xml" );
		builder = builder.addModule(binder -> binder.bind(SchemaUpdateStrategy.class).to(CreateIfNoSchemaStrategy.class));

//		builder = builder.jdbcDriver("org.hsqldb.jdbcDriver");
//		builder = builder.user("sa");
//		builder = builder.password("");
//		builder = builder.url("jdbc:hsqldb:mem:aname");

		builder = builder.jdbcDriver( "org.h2.Driver" );
		builder = builder.url( "jdbc:h2:mem:test" );

		ServerRuntime serverRuntime = builder.build();

		// Make sure everything works as expected
		ObjectContext oc = serverRuntime.newContext();

		// 1. Test creation of a new object.
		Invoice newInvoice = oc.newObject( Invoice.class );
		newInvoice.setYear( 2016 );
		newInvoice.setInvoiceNumber( 1 );
		oc.commitChanges();

		// 2. Test fetch of that object.
		ObjectSelect.query( Invoice.class ).select( oc ).forEach( System.out::println );
	}
}