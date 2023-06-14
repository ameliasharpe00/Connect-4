Name: Amelia G L Sharpe
PennKey: amsharpe
Recitation: 204
Game: Connect4: uses MVC style

1. Model: 
- uses an int 2D array to store game state. i.e, when player1 plays,
an int 1 is put into the array while when player w plays,
an int 2 is put into the array. An int array was used so that I could easily use ==
rather than using .equals for strings. With strings, it is easier to make mistakes
by accidentally using == because the code will still compile. Overall, I used
the 2D array because it is great for checking for wins, errors and just the game state by
using a nested for loop to iterate over rows and columns.
- after every turn is played, it checks for a winner. It checks
for the winner by checking horizontally, vertically, forward
diagonally and backward diagonally (hence there are 4 different
nested for loops under the method checkWinner())
- when someone wins, the player can no longer input anything 
- I used a LinkedList() with static type LinkedList() so that I 
could use methods such as pollLast() to remove the last element
placed into the list. The LinkedList stored a list of int[].
this int[] stored 3 things in this order: {row, col, playerNumber}. 
This way, when a player hits undo, I can easily access change the 
state at that index back to a zero and repaint a white circle. I used collections
(LinkedList) because it is easy to remove elements from it. I also
was interested in having the elements in order so that's another reason for
using the LinkedList. Ultimately, one of my goals was to remove the most 
 recent move played by the user, and LinkedList was a great fit.
- has a pause() method which when called, 2 things get saved:
the 2D grid entries, as well as the number of moves. Using File I/O, 
both these states are overwritten(not appended) onto a txt file called pause.txt. 
This method uses a FileWriter and a BufferedWriter
- has a resume() method which when called, 2 things are restored: 
the 2D grid entries and the number of moves which were made before the
 game was paused. It used a FileReader and a BufferedReader
 - I handles exceptions without throwing them because I did not want the game
 to crash. As such, I handles them by displaying a popup(JOPtionPane) or by
 printing to the console a message such as "error when resuming game).
- the reset() method clears all state and creates a new game
- the other methods implemented are there so that the programmer can
 understand or debug the code better (such as printGameState, getCurrentPlayer)

 2. View:
 - Uses Java Swing and awt to draw widgets like JButton, Jframe, JPanel,
 JOptionPane and JLabel. Attached the buttons are mouse click listeners.
 When the user clicks on each button, something different happens such as
 pausing the game or undoing or showing instructions.
 - It starts off by displaying a JOptionPane asking the two players for
 their names
 - Displays a 6 x 7 grid with a JPanel north which displays the buttons which
 are reset, pause, resume, instructions and undo
 - When player 1 plays, a blue circle is dropped into that column. When 
 player 2 plays, a pink circle is dropped.
 - A label at the button shows in real time whose turn it is and who wins or
 when it is a time
 - Another label at the top shows in real time the number of moves made
 - After every move, the entire board is repainted so as to show updates

 3. Controller
 - Uses mouse listeners only to control the game.
 - When a user clicks anywhere along a column, the controller triggers a method
 from the model which will cause the grid to be updated with the move.
 For example, when the user clicks in the 3rd column, the mouse coordinates are
 stored, then these coordinates are fed into the playTurn(int c) method which
 is found in the model. This causes the grid to be updated with that move.
 After that, still within the mouse listener, the whole grid is repainted but now
 with that move played so that the users can view it. After that, the number
 of moves is also increased and that is changed as well.