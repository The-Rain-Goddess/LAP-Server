/**
 * 
 */
package com.rain.app.server.redux.gui;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.rain.app.server.redux.Listener;
import com.rain.app.server.redux.MapListener;
import com.rain.app.server.redux.ServerRedux;
import com.rain.app.server.redux.SummonerData;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.control.Button;
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
	private static final int WINDOW_MIN_HEIGHT = 700, WINDOW_MIN_WIDTH = 1300;
	private final static Insets DEFAULT_INSETS = new Insets(10,10,10,10);
	private  WebView browser;
	private  WebEngine engine;
	private BorderPane mainBorderPane;
	private TableView<SummonerData> table;
	private TextField search;
	
	private final String LOG_PATH = "Q:\\Workspace\\LAP-Server (git)\\LAP-Server\\LAP-Server\\log\\Logging.html";

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
	public void start(Stage root){
		try{
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
		} catch(Exception e){
			e.printStackTrace();
		}
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
		mainBorderPane = new BorderPane();
		//mainBorderPane.setTop(getArchiveControls());
		//mainBorderPane.setRight(getArchiveData());
		//mainBorderPane.setLeft(getArchiveFileSystem());
		mainBorderPane.setRight(setStorageData(ServerRedux.getSummonerDataStorageAsList()));
		mainBorderPane.setLeft(getLog());
		return mainBorderPane;
	}
	
	public void updateStorageData(){
		//mainBorderPane.setRight(setStorageData(ServerRedux.getSummonerDataStorageAsList()));
		
		//colums
		table.getColumns().clear();
		table.getColumns().setAll(getTableColumns());
		
		//sort and filter the data
		SortedList<SummonerData> sortedData = getSortedData(ServerRedux.getSummonerDataStorageAsList());
		
		// Bind the SortedList comparator to the TableView comparator
		sortedData.comparatorProperty().bind(table.comparatorProperty());
		
		// Show the Data
		table.setItems(sortedData);
	}

//Storage Data
	private VBox setStorageData(List<SummonerData> dataForTable){
		VBox container = new VBox();
		HBox controls = new HBox();
		controls.setAlignment(Pos.TOP_RIGHT);
		search = new TextField();
		Button reload = new Button();
		try{
			reload.setGraphic(new ImageView(new Image("com/rain/app/server/redux/reload32_32.png")));
		}catch(Exception e){
			e.printStackTrace();
		}
		reload.setOnAction((ActionEvent e)->{
			updateStorageData();
			
		});
		
		((MapListener<String, SummonerData>)ServerRedux.getSummonerDataStorage()).addListener(new Listener(){
			@Override
			public <K> void invoke(K key) {
				Platform.runLater(()->{
					updateStorageData();
				});
			}
		});
		
		controls.getChildren().addAll(new Label("Search"), search, reload);
		HBox.setMargin(search, new Insets(0,10,0,10));
		HBox.setMargin(reload, new Insets(0,5,0,10));
		
		table = new TableView<SummonerData>();
		table.setId("Data Table");
		table.setMinHeight(WINDOW_MIN_HEIGHT - 150);
		table.setMinWidth(WINDOW_MIN_WIDTH / 4 + 45);
		
		//ititialize columns
		table.getColumns().setAll(getTableColumns());
		
		if(dataForTable!=null){
			//sort and filter the data
			SortedList<SummonerData> sortedData = getSortedData(dataForTable);
			
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
	
	private SortedList<SummonerData> getSortedData(List<SummonerData> list){
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
		browser.setMaxWidth(WINDOW_MIN_WIDTH / 4 * 3 );
		browser.setMinWidth(WINDOW_MIN_WIDTH / 4 * 3 - 50);
		engine.load(new File(LOG_PATH).toURI().toString());
		
		Button reload = new Button();
		
		try{ reload.setGraphic(new ImageView(new Image("com/rain/app/server/redux/reload32_32.png")));}
		catch(Exception e){ e.printStackTrace(); }
		
		reload.setOnAction((ActionEvent e)-> {
			engine.reload();
		});
		
		VBox.setMargin(browser, DEFAULT_INSETS);
		container.getChildren().addAll(reload, browser);
		return container;
	}
	
	public void updateLog(){
		System.out.println("Loading log...");
		engine.reload();
		System.out.println("Loaded log.");
	}
	
}
