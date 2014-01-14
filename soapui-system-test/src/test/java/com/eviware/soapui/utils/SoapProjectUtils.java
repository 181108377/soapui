/*
 * SoapUI, copyright (C) 2004-2014 smartbear.com
 *
 * SoapUI is free software; you can redistribute it and/or modify it under the
 * terms of version 2.1 of the GNU Lesser General Public License as published by
 * the Free Software Foundation.
 *
 * SoapUI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details at gnu.org.
 */
package com.eviware.soapui.utils;

import com.eviware.soapui.SoapUI;
import org.fest.swing.core.Robot;
import org.fest.swing.fixture.*;

/**
 * Utility class used for generic operations on a SOAP project
 */
public class SoapProjectUtils
{
	private static final String NEW_SOAP_PROJECT_MENU_ITEM_NAME = "New SOAP Project";
	private static final String NEW_SOAP_PROJECT_DIALOG_NAME = "New SOAP Project";
	private static final String OK_BUTTON_NAME = "OK";
	private static final String WSDL_FIELD_NAME = "Initial WSDL";
	private static final String ROOT_FOLDER = SoapProjectUtils.class.getResource( "/" ).getPath();
	private static final String TEST_WSDL = ROOT_FOLDER + "test.wsdl";
	private static final String WORKSPACE_NAME = SoapUI.getWorkspace().getName();
	private static final String PROJECT_NAME = "test";
	private static final String INTERFACE_NAME = "GeoCode_Binding";
	private static final String OPERATION_NAME = "geocode";
	private static final String REQUEST_NAME = "Request 1";
	private static final String OPERATION_PATH = WORKSPACE_NAME + "/" + PROJECT_NAME + "/" + INTERFACE_NAME + "/" + OPERATION_NAME;
	private static final String REQUEST_PATH = WORKSPACE_NAME + "/" + PROJECT_NAME + "/" + INTERFACE_NAME + "/" + OPERATION_NAME + "/" + REQUEST_NAME;
	private static final int NEW_PROJECT_TIMEOUT = 2000;

	private final Robot robot;
	private final WorkspaceUtils workspaceUtils;

	public SoapProjectUtils( Robot robot )
	{
		this.robot = robot;
		this.workspaceUtils = new WorkspaceUtils();
	}

	public void createNewSoapProject( FrameFixture rootWindow )
	{
		openCreateNewSoapProjectDialog( rootWindow );
		enterProjectNameAndWsdlUrlAndClickOk();
	}

	public void openRequestEditor( FrameFixture rootWindow )
	{
		JTreeFixture tree = workspaceUtils.getNavigatorPanel( rootWindow ).tree();

		waitForProjectToLoad();

		tree.expandPath( OPERATION_PATH );
		tree.node( REQUEST_PATH ).doubleClick();
	}

	private void openCreateNewSoapProjectDialog( FrameFixture rootWindow )
	{
		JPopupMenuFixture workspace = workspaceUtils.rightClickOnWorkspace( rootWindow );
		workspace.menuItem( FestMatchers.menuItemWithText( NEW_SOAP_PROJECT_MENU_ITEM_NAME ) ).click();
	}

	private void enterProjectNameAndWsdlUrlAndClickOk()
	{
		DialogFixture newProjectDialog = FestMatchers.dialogWithTitle( NEW_SOAP_PROJECT_DIALOG_NAME )
				.withTimeout( NEW_PROJECT_TIMEOUT ).using( robot );

		newProjectDialog.textBox( WSDL_FIELD_NAME ).setText( TEST_WSDL );

		JButtonFixture buttonOK = newProjectDialog.button( FestMatchers.buttonWithText( OK_BUTTON_NAME ) );
		buttonOK.click();
	}

	// There might be a more elegant way to wait
	private void waitForProjectToLoad()
	{
		try
		{
			Thread.sleep( NEW_PROJECT_TIMEOUT );
		}
		catch( InterruptedException e )
		{
			e.printStackTrace();
		}
	}
}