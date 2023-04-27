# Command GUI Buttons

Command GUI Buttons is a fabric mod for Minecraft that allows users to create custom command buttons on their clients. Users can bring up this menu in-game with the G key, type in the Name and Command they want to create a button for, and press the + to create. It will then show up as a button on their screen to use whenever they want, as the button will automatically type and execute the command they initially entered.

![mcss](https://user-images.githubusercontent.com/16052684/234972699-a2be88ae-e4df-4130-b5ce-0b881b827810.png)

Create buttons for preset chat messages, commands like '/warp home', or anything else that goes in the chat box.

## How to Use:

  - Bring up the menu in-game with the G key
  - Type in the Name and Command you want to save into a button
  - Press the + button



### How It Works
The list of commands are serialized into a JSON format and stored locally on the users’ systems. The mod writes any new commands into the JSON as new buttons are created, and loads the JSON at the start of the game. A local list instance exists for reading and loading the buttons each time, so that file reading is kept to a minimum.
