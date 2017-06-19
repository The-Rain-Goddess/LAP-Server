/**
 * 
 */
package com.rain.app.server.redux.gui;

import java.util.Arrays;
import java.util.List;

import com.rain.app.server.redux.ServerRedux;
import com.rain.app.server.redux.SummonerData;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author Ryan May
 *
 */
public class MainWindow extends Application {
	private static final int WINDOW_MIN_HEIGHT = 700, WINDOW_MIN_WIDTH = 1000;
	private final static Insets DEFAULT_INSETS = new Insets(10,10,10,10);
	private  WebView browser;
	private  WebEngine engine;

	/**
	 * 
	 */
	
	public void begin(String[] args){
		launch(args);
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage root) throws Exception {
		AnchorPane mainComponentPane = getMainComponentPane();
		
		Scene mainScene = new Scene(mainComponentPane, WINDOW_MIN_WIDTH, WINDOW_MIN_HEIGHT);
		
		root.setScene(mainScene);
		root.setOnCloseRequest((WindowEvent e) ->{
			ServerRedux.stopRunning();
			//Platform.exit();
			try{
				root.hide();
				root.close();
			} catch(RuntimeException e1){
				System.out.println("Runtime Exception encountered on window close.");
			}
		}); root.show();
	}
	
	private AnchorPane getMainComponentPane(){
		AnchorPane mainComponentPane = new AnchorPane();
		mainComponentPane.setMinWidth(WINDOW_MIN_WIDTH);
		mainComponentPane.setMinHeight(WINDOW_MIN_HEIGHT);
		mainComponentPane.getChildren().add(getMainVerticalBox());
		return mainComponentPane;
	}
	
	private VBox getMainVerticalBox(){
		VBox vbox = new VBox();
		vbox.getChildren().addAll(getMenuBar(), getMainBorderPane());
		return vbox;
	}
	
//MenuBar
	private MenuBar getMenuBar(){
		MenuBar menuBar = new MenuBar();
        
        // --- Menu File
        Menu menuFile = getMenuFileOption();
        
        // --- Menu Edit
        Menu menuEdit = getMenuEditOption();
 
        // --- Menu View
        Menu menuView = getMenuViewOption();
        
        menuBar.setMinWidth(WINDOW_MIN_WIDTH);
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        menuBar.autosize();
        return menuBar;
	}
	
	private Menu getMenuFileOption(){
		Menu menu = new Menu("File");
		
		MenuItem add = getMenuItem("Add", new MenuAction(){
			@Override
			public void execute() {
				
			}
		});
		
		MenuItem remove = getMenuItem("Remove", new MenuAction(){
			@Override
			public void execute() {
				
			}
		});
		
	    menu.getItems().addAll(add, remove);
	    return menu;
	}
	
	private MenuItem getMenuItem(String name, MenuAction action){
		MenuItem item;
		try { 
			item = new MenuItem(name, new ImageView(new Image("com/rain/app/server/redux/res" + name.toLowerCase()+".png")));
		} catch(IllegalArgumentException e){
			item = new MenuItem(name);
		}
				
		item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
			public void handle(ActionEvent t) {
                action.execute();
            }
        });
        return item;
	}
	
	private Menu getMenuEditOption(){
		Menu menu = new Menu("Edit");
		return menu;
	}
	
	private Menu getMenuViewOption(){
		Menu menu = new Menu("View");
		return menu;
	}
	
//Main border Pane
	private BorderPane getMainBorderPane(){
		BorderPane mainBorderPane = new BorderPane();
		//mainBorderPane.setTop(getArchiveControls());
		//mainBorderPane.setRight(getArchiveData());
		//mainBorderPane.setLeft(getArchiveFileSystem());
		mainBorderPane.setRight(getStorageData(ServerRedux.getSummonerDataStorageAsList()));
		mainBorderPane.setLeft(getLog());
		return mainBorderPane;
	}

//Storage Data
	private VBox getStorageData(List<SummonerData> dataForTable){
		VBox container = new VBox();
		HBox controls = new HBox();
		controls.setAlignment(Pos.TOP_RIGHT);
		final TextField search = new TextField();
		controls.getChildren().addAll(new Label("Search"), search);
		HBox.setMargin(search, new Insets(0,10,0,10));
		
		final TableView<SummonerData> table = new TableView<SummonerData>();
		table.setId("Data Table");
		table.setMinHeight(WINDOW_MIN_HEIGHT-150);
		table.setMinWidth(WINDOW_MIN_WIDTH / 2);
		
		//ititialize columns
		table.getColumns().setAll(getTableColumns());
		
		if(dataForTable!=null){
			//sort and filter the data
			SortedList<SummonerData> sortedData = getSortedData(search, dataForTable);
			
			// Bind the SortedList comparator to the TableView comparator
			sortedData.comparatorProperty().bind(table.comparatorProperty());
			
			// Show the Data
			table.setItems(sortedData);
		}
		container.getChildren().addAll(controls, table);
		VBox.setMargin(controls, DEFAULT_INSETS);
		return container;
	}
	
	private List<TableColumn<SummonerData, ?>> getTableColumns(){
		//Column 1: Name
		TableColumn<SummonerData, String> nameCol = new TableColumn<SummonerData, String>("Name");
		nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSummonerName()));
		
		//Column 2: ID
		TableColumn<SummonerData, Number> idCol = new TableColumn<SummonerData, Number>("ID");
		idCol.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getSummonerId()));
		
		//Column 3: Match Count
		TableColumn<SummonerData, Number> matchListCol = new TableColumn<SummonerData, Number>("Match Count");
		matchListCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getMatchList().size()));
		
		
		return Arrays.asList(nameCol, idCol, matchListCol);		
	}
	
	private SortedList<SummonerData> getSortedData(TextField search, List<SummonerData> list){
		//get the data
		List<SummonerData> data = list;
				
		//wrap the ObservableList in a FilteredList
		FilteredList<SummonerData> filteredData = new FilteredList<SummonerData>(FXCollections.observableList(data), p -> true);
		
		//set the filter Predicate whenever the filter changes
		search.textProperty().addListener((observable, oldValue, newValue)-> {
			filteredData.setPredicate(summonerData -> {
				//If filter is empty, display all
				if(newValue == null || newValue.isEmpty()){
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				if(summonerData.getSummonerName().toLowerCase().contains(lowerCaseFilter))
					return true;
				else if(Long.toString(summonerData.getSummonerId()).toLowerCase().contains(lowerCaseFilter))
					return true;
				
				else return false;
			});
		});
		
		//wrap the filtered list in a sorted list
		SortedList<SummonerData> sortedData = new SortedList<SummonerData>(filteredData);
		
		return sortedData;
	}

//HTML log
	private VBox getLog(){
		VBox container = new VBox();
		
		browser = new WebView();
		engine = browser.getEngine();
		browser.setMaxWidth(WINDOW_MIN_WIDTH / 2 - 50);
		engine.load("Q:\\Workspace\\LAP-Server (git)\\LAP-Server\\LAP-Server\\log\\Logging.html");
		
		VBox.setMargin(browser, DEFAULT_INSETS);
		container.getChildren().add(browser);
		return container;
	}
	
	public void updateLog(){
		engine.load("Q:\\Workspace\\LAP-Server (git)\\LAP-Server\\LAP-Server\\log\\Logging.html");
	}
	
}
