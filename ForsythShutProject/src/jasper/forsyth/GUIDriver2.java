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
	boolean singledie = false;

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
		Label answer = new Label("");
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
			// adds up markers value if the makers are not down 
			for (Tile t : markers) {
				if (!t.isDown()) {
					roundsum += t.getValue();
				}
				t.reset(); // too reset the markers back to original state

			}
			// reset the buttons
			for (Button b : tileBtns) {
				b.setStyle("-fx-background-color: #FFFFFF;");
				b.setDisable(false);
			}

			// sees if the round is less then 3 then makes it 1 more
			if (currentplayer == 1 && currentround < 3) {
				currentround++;
				p1s += roundsum;
				
			
			// sees if player is 1 and then switches and resets round amount
			} else if (currentplayer == 1) {
				p1s += roundsum;
				currentplayer = 2;
				currentround = 1;
			}
			// sees if player is 2 and round is less then 3 then increase round and score 
			else if (currentplayer == 2 && currentround < 3) {
				currentround++;
				p2s += roundsum;
				
			} else {
				p2s += roundsum; // adds final round score
				if (p1s > p2s) {
					messageover.setText("Player Two Won" + " Player one score: " + p1s + " Player two score: " + p2s);
				} else if (p2s > p1s) {
					messageover.setText("Player One Won" + " Player one score: " + p1s + " Player two score: " + p2s);
				} else {
					messageover.setText("Its A Tie" + " Player one score: " + p1s + " Player two score: " + p2s);
				}

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
			// checks to see if 7,8,9 are shut
			if (markers[6].isDown() == true && markers[7].isDown() == true && markers[8].isDown() == true) {
				singledie = true;

			}
			else {
				// it is not a single die 
				singledie= false;
			}
			// switches button text based on whether or not rolling one or two die 
			if (singledie) {
				btnRoll.setText("Roll 1 die");
			} else {
				btnRoll.setText("Roll 2 die");
			}

		});
		btnRoll.setOnAction(e -> {

			int sumDie = 0;
			// if just single die will only roll one die 
			if (singledie) {
				sumDie = d1.roll();
			}
			// rolls two die 
			else {
				sumDie = d1.roll() + d2.roll();
			}

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
