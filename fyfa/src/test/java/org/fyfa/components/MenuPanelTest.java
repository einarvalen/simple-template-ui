package org.fyfa.components;

import static org.junit.Assert.assertTrue;

import org.fyfa.Context;
import org.fyfa.components.MenuPanel;
import org.junit.Test;

public class MenuPanelTest {
	private final Context context = new Context();

	@Test
	public void render() throws Exception {
		MenuPanel menuPanelA = createmenuPanelA();
		MenuPanel menuPanelB = createmenuPanelB();
		String html = menuPanelA.render() + menuPanelB.render();
		verifyRenderedHtml( html );
	}

	private void verifyRenderedHtml( String html ) {
		assertTrue( html.indexOf( "$" ) == -1 );
		assertTrue( html.indexOf( "%s" ) == -1 );
		assertTrue( html.indexOf( "My First Menu" ) > -1 );
		assertTrue( html.indexOf( "Menu Item two" ) > -1 );
		assertTrue( html.indexOf( "Help display" ) > -1 );
		assertTrue( html.indexOf( "index.jsp" ) > -1 );
		//System.out.println( html );
		//TableTest.view( html );
	}

	private MenuPanel createmenuPanelB() {
		MenuPanel menuPanelB = new MenuPanel( context, "My Second Menu" );
		menuPanelB.add( "Menu Item One", "Help display", "index.jsp" );
		menuPanelB.add( "Menu Item two", "Help display", "index.jsp" );
		return menuPanelB;
	}

	private MenuPanel createmenuPanelA() {
		MenuPanel menuPanelA = new MenuPanel( context, "My First Menu" );
		menuPanelA.add( "Menu Item One", "Help display", "index.jsp" );
		menuPanelA.add( "Menu Item two", "Help display", "index.jsp" );
		return menuPanelA;
	}

}
