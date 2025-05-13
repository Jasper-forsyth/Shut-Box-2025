package jasper.forsyth;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIDriver2 extends Application {

	Die d1 = new Die(6);
	Die d2 = new Die(6);

	int p1s = 0;
	int p2s = 0;

	int currentplayer = 1;
	int currentround = 1;

	boolean gameover = false;

	@Override
	public void start(Stage stage) throws Exception {

		VBox root = new VBox(10);
		root.setAlignment(Pos.CENTER);
		// Create the Button objects for each marker
		Button[] tileBtns = new Button[9];
		for (int i = 0; i < tileBtns.length; i++) {
			Button button = new Button(String.valueOf(i + 1));
			button.setStyle("-fx-background-color: #FFFFFF;");
			tileBtns[i] = button;
		}
		Label answer= new Label("");
		// Create the markers
		Tile[] markers = new Tile[9];
		for (int i = 0; i < markers.length; i++) {
			markers[i] = new Tile(i + 1);
		}

		// label for which player it is
		Label lblPlayernum = new Label("player num: " + currentplayer);
		// label for what round it is
		Label lblRoundnum = new Label("Current Round: " + currentround);

		HBox statusbox = new HBox(40);

		lblPlayernum.setAlignment(Pos.CENTER_LEFT);
		lblRoundnum.setAlignment(Pos.CENTER_RIGHT);
		statusbox.getChildren().addAll(lblPlayernum, lblRoundnum);

		HBox btnBox = new HBox(10);
		for (Button b : tileBtns) {
			btnBox.getChildren().add(b);

			// add event handler
			b.setOnAction(e -> {
				int number = Integer.valueOf(b.getText());
				if (!markers[(number - 1)].isDown()) {
					if (!markers[(number - 1)].isSelected()) {
						// set the color for selection
						b.setStyle("-fx-background-color: #DAF7A6;");
						markers[(number - 1)].select();
					} else {
						b.setStyle("-fx-background-color: #FFFFFF;");
						markers[(number - 1)].deselect();
					}
				}
			});
		} // end of event handler for tile section buttons

		Button btnRoll = new Button("Roll 2 die");

		Label dieResult = new Label("");

		Button quit = new Button("Quit");
		VBox over = new VBox(10);
		Label messageover = new Label("OVER");
		over.setAlignment(Pos.CENTER);
		over.getChildren().add(messageover);
		Scene sceneover = new Scene(over, 500, 500);

		quit.setOnAction(e -> {
			int roundsum = 0;
			for (Tile t : markers) {
				if (!t.isDown()) {
					roundsum += t.getValue();
				}
			}
			p1s += roundsum;
			System.out.println(p1s);
			if (currentround < 3) {
				currentround++;

			}

			else if (currentplayer == 1) {
				currentplayer = 2;
				currentround = 1;
			} else if (currentplayer == 2) {
				messageover.setText(String.valueOf(p1s));
				stage.setScene(sceneover);
			}

			lblPlayernum.setText("player num: " + currentplayer);
			lblRoundnum.setText("Current Round: " + currentround);

		});

		Button lockIn = new Button("Lock IN");
		if (dieResult.getText().isEmpty()) {
			lockIn.setDisable(true);
		}

		lockIn.setOnAction(e -> {
			// determine the sum of the selected tiles
			int sum = 0;
			for (Tile t : markers) {
				if (t.isSelected()) {
					sum += t.getValue();
				}
			}
			System.out.println(sum);

			System.out.println(Integer.valueOf(dieResult.getText()));

			if (sum == Integer.valueOf(dieResult.getText())) {
				// disable the buttons that are selected and change color
				for (int i = 0; i < markers.length; i++) {
					if (markers[i].isSelected()) {
						markers[i].deselect();
						markers[i].shut();
						tileBtns[i].setStyle("-fx-background-color:#ff99ff;");
						tileBtns[i].setDisable(true);
					}
				}
				// renable the roll
				dieResult.setText("");
				btnRoll.setDisable(false);
				answer.setText("Good match");
			} else {
				// clear the state of the buttons and provide feedback
				answer.setText("Bad match");

			}

		});
		btnRoll.setOnAction(e -> {
		
				int sumDie = d1.roll() + d2.roll();
				dieResult.setText(String.valueOf(sumDie));
				btnRoll.setDisable(true);
				// enable to potential lockin button

				lockIn.setDisable(false);
			
		});

		btnBox.setAlignment(Pos.CENTER);
		statusbox.setAlignment(Pos.CENTER);

		root.getChildren().add(statusbox);
		root.getChildren().add(btnBox);
		root.getChildren().add(btnRoll);
		root.getChildren().add(dieResult);
		root.getChildren().add(lockIn);
		root.getChildren().add(quit);
		root.getChildren().add(answer);

		Scene scene = new Scene(root, 500, 500);

		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch();

	}

}
