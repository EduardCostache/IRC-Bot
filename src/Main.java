import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Main
{
    private static final String nick = "TheBot";
    private static final String username = "TheBot";
    private static final String realName = "TheBot";
    private static final String prefix = "+";
    private static final String server = "127.0.0.1";
    // Here is the channel, NOTE, the hashtag need not to be entered.
    private static String room = "ss";
    private static PrintWriter out;
    private static Scanner in;

    public static void main(String[] args) throws IOException
    {
	    Scanner console = new Scanner(System.in);

//	    System.out.print("Enter your room: ");
//	    room = console.nextLine();

        // The socket used to connect to the server
        Socket socket = new Socket(server, 6667);

        out = new PrintWriter(socket.getOutputStream(), true);
        in = new Scanner(socket.getInputStream());

        // The commands needed to enter the server
        write("NICK ", nick);
        write("USER ", username + " 0 * :" + realName);
        write("JOIN ", "#" + room);

        // When the bot joins it will display some commands
        welcomeMessage("#"+room);

        // This is the loop where the commands happen, it listens if anyone wrote the specific commands then executes code
        while(in.hasNext())
        {
            String serverMessage = in.nextLine();
            System.out.println("<<< " + serverMessage);

            //------------------------------------------COMMANDS-----------------------------------\\

            // Hello Command
            if(serverMessage.contains(prefix+"hello"))
            {
                String user = getUser(serverMessage);
                multiRoomMessage("Hello There " + user, getRoom(serverMessage));
            }

            // serverHello Command
            else if(serverMessage.contains(prefix+"serverHello"))
            {
                try
                {
                    String message;
                    write("LIST ", "");
                    Scanner scanner = new Scanner(socket.getInputStream());
                    while(scanner.hasNext())
                    {
                        message = list.getChannels(scanner.nextLine());
                        if(message.startsWith("#"))
                        {
                            multiRoomMessage("Test!", message);
                        }
                    }
                    scanner.close();
                    multiRoomMessage("Done!", getRoom(serverMessage));
                }
                catch(Exception e)
                {
                    multiRoomMessage("Something went wrong! Try again.", getRoom(serverMessage));
                }
            }

            // Invite Command
            else if(serverMessage.contains(prefix+"invite"))
            {
                try
                {
                    Scanner scanner = new Scanner(socket.getInputStream());
                    write("INVITE", invite.getNick(serverMessage) + "  #" +room);
                    if(scanner.hasNext())
                    {
                        if(invite.getError(scanner.nextLine(), room).contains("operator"))
                        {
                            multiRoomMessage("Sorry I am not an operator, can't invite", getRoom(serverMessage));
                        }
                        multiRoomMessage(invite.getError(scanner.nextLine(), room), getRoom(serverMessage));
                    }
                    scanner.close();
                }
                catch (StringIndexOutOfBoundsException e)
                {
                    multiRoomMessage("Type a username to invite someone.", getRoom(serverMessage));
                }
            }

            // Join Command
            else if(serverMessage.contains(prefix+"join"))
            {
                try
                {
                    write("JOIN ", join.getChannel(serverMessage));
                    multiRoomMessage("Joined channel "+join.getChannel(serverMessage), getRoom(serverMessage));
                    welcomeMessage(join.getChannel(serverMessage));
                }
                catch (StringIndexOutOfBoundsException e)
                {
                    multiRoomMessage("You need to enter a channel to join.", getRoom(serverMessage));
                }
            }

            // Time Command
            else if(serverMessage.contains(prefix+"time"))
            {
                write("TIME ", "");
                multiRoomMessage("The time is: "+time.getTime(in.nextLine()), getRoom(serverMessage));
            }

            // Create command
            else if(serverMessage.contains(prefix+"create"))
            {
                String user = getUser(serverMessage);
                createFile(user, 1000, serverMessage);
            }

            // Joke command
            else if(serverMessage.contains(prefix+"joke"))
            {
                Random random = new Random();
                int n = random.nextInt(5);
                if(n == 1)
                {
                    multiRoomMessage("How about the new restaurant called 'Karma'. There's no menu: You get what you deserve.", getRoom(serverMessage));
                }
                else if(n == 2)
                {
                    multiRoomMessage("Surveys say that 4 out of 5 people suffer from diarrhea. That means the 5th one likes it.", getRoom(serverMessage));
                }
                else if(n == 3)
                {
                    multiRoomMessage("They laughed at my crayon drawing. I laughed at their chalk outline.", getRoom(serverMessage));
                }
                else if(n == 4)
                {
                    multiRoomMessage("I’m not going to bungee jump. I was born because of broken rubber and i’m not gonna die the same way.", getRoom(serverMessage));
                }
                else
                {
                    multiRoomMessage("If I had a dollar for every girl that found me unattractive, they would find me attractive.", getRoom(serverMessage));
                }
            }

            // Coinflip command
            else if(serverMessage.contains(prefix+"coinflip"))
            {
                File file = new File("resources\\"+getUser(serverMessage)+".txt");
                if(file.exists())
                {
                    try
                    {
                        int userBalance = getUserBalance(serverMessage);
                        String userSide = coinflip.getSide(coinflip.format(serverMessage).toLowerCase());
                        userSide = userSide.toLowerCase();
                        int userBet = coinflip.getBet(coinflip.format(serverMessage));

                        if (userSide.equals("Head".toLowerCase()) || userSide.equals("Tail".toLowerCase()))
                        {
                            boolean heads = new Random().nextInt(2) == 0;
                            if (!(userBet > userBalance) && !(userBet == 0) && !(userBet < 0))
                            {
                                if (heads)
                                {
                                    if (userSide.equals("Head".toLowerCase()))
                                    {
                                        userBalance += userBet;
                                        deleteFile(getUser(serverMessage));
                                        createFile(getUser(serverMessage), userBalance, serverMessage);
                                        multiRoomMessage("Coin landed on Head, You Win " + "+" + userBet, getRoom(serverMessage));
                                    } else
                                    {
                                        userBalance -= userBet;
                                        deleteFile(getUser(serverMessage));
                                        createFile(getUser(serverMessage), userBalance, serverMessage);
                                        multiRoomMessage("Coin landed on Head, You Lose " + "-" + userBet, getRoom(serverMessage));
                                    }
                                }
                                else
                                {
                                    if (userSide.equals("Tail".toLowerCase()))
                                    {
                                        userBalance += userBet;
                                        deleteFile(getUser(serverMessage));
                                        createFile(getUser(serverMessage), userBalance, serverMessage);
                                        multiRoomMessage("Coin landed on Tail, You Win " + "+" + userBet, getRoom(serverMessage));
                                    }
                                    else
                                    {
                                        userBalance -= userBet;
                                        deleteFile(getUser(serverMessage));
                                        createFile(getUser(serverMessage), userBalance, serverMessage);
                                        multiRoomMessage("Coin landed on Tail, You Lose " + "-" + userBet, getRoom(serverMessage));
                                    }
                                }
                            }
                            else if (userBet == 0)
                            {
                                if (heads)
                                {
                                    multiRoomMessage("Coin landed on Head", getRoom(serverMessage));
                                }
                                else
                                {
                                    multiRoomMessage("Coin landed on Tail", getRoom(serverMessage));
                                }
                            }
                            else
                            {
                                multiRoomMessage(getUser(serverMessage) + " you don't have enough funds to bet " + userBet, getRoom(serverMessage));
                            }
                        }
                        else
                        {
                            multiRoomMessage(getUser(serverMessage) + " please pick a valid side of the coin.", getRoom(serverMessage));
                        }
                    }
                    catch (StringIndexOutOfBoundsException e)
                    {
                        multiRoomMessage("You need to enter a bet and choose a coin side.", getRoom(serverMessage));
                    }
                }
                else
                {
                    multiRoomMessage("You need to create an account first.", getRoom(serverMessage));
                }
            }

            // RollDice command
            else if(serverMessage.contains(prefix+"rolldice"))
            {
                File file = new File("resources\\"+getUser(serverMessage)+".txt");
                if(file.exists())
                {
                    try
                    {
                        int userBalance = getUserBalance(serverMessage);
                        int userBet = dice.getBet(dice.format(serverMessage));
                        int n = new Random().nextInt(6);
                        int x = new Random().nextInt(2);

                        if (!(userBet > userBalance) && !(userBet == 0) && !(userBet < 0))
                        {
                            if (n == 1)
                            {
                                if (x == 1)
                                {
                                    userBalance += userBet;
                                    deleteFile(getUser(serverMessage));
                                    createFile(getUser(serverMessage), userBalance, serverMessage);
                                    multiRoomMessage("Dice rolled on 1 and it's White you earn " + "+" + userBet, getRoom(serverMessage));
                                }
                                else
                                {
                                    userBalance -= userBet;
                                    deleteFile(getUser(serverMessage));
                                    createFile(getUser(serverMessage), userBalance, serverMessage);
                                    multiRoomMessage("Dice rolled on 1 and it's Black you lose " + "-" + userBet, getRoom(serverMessage));
                                }
                            }
                            else if (n == 2)
                            {
                                if (x == 1)
                                {
                                    userBalance += userBet * 2;
                                    deleteFile(getUser(serverMessage));
                                    createFile(getUser(serverMessage), userBalance, serverMessage);
                                    multiRoomMessage("Dice rolled on 2 and it's White you earn " + "+" + userBet * 2, getRoom(serverMessage));
                                }
                                else
                                {
                                    userBalance -= userBet * 2;
                                    deleteFile(getUser(serverMessage));
                                    createFile(getUser(serverMessage), userBalance, serverMessage);
                                    multiRoomMessage("Dice rolled on 2 and it's Black you lose " + "-" + userBet * 2, getRoom(serverMessage));
                                }
                            }
                            else if (n == 3)
                            {
                                if (x == 1)
                                {
                                    userBalance += userBet * 3;
                                    deleteFile(getUser(serverMessage));
                                    createFile(getUser(serverMessage), userBalance, serverMessage);
                                    multiRoomMessage("Dice rolled on 3 and it's White you earn " + "+" + userBet * 3, getRoom(serverMessage));
                                }
                                else
                                {
                                    userBalance -= userBet * 3;
                                    deleteFile(getUser(serverMessage));
                                    createFile(getUser(serverMessage), userBalance, serverMessage);
                                    multiRoomMessage("Dice rolled on 3 and it's Black you lose " + "-" + userBet * 3, getRoom(serverMessage));
                                }
                            }
                            else if (n == 4)
                            {
                                if (x == 1)
                                {
                                    userBalance += userBet * 4;
                                    deleteFile(getUser(serverMessage));
                                    createFile(getUser(serverMessage), userBalance, serverMessage);
                                    multiRoomMessage("Dice rolled on 4 and it's White you earn " + "+" + userBet * 4, getRoom(serverMessage));
                                }
                                else
                                {
                                    userBalance -= userBet * 4;
                                    deleteFile(getUser(serverMessage));
                                    createFile(getUser(serverMessage), userBalance, serverMessage);
                                    multiRoomMessage("Dice rolled on 4 and it's Black you lose " + "-" + userBet * 4, getRoom(serverMessage));
                                }
                            }
                            else if (n == 5)
                            {
                                if (x == 1)
                                {
                                    userBalance += userBet * 5;
                                    deleteFile(getUser(serverMessage));
                                    createFile(getUser(serverMessage), userBalance, serverMessage);
                                    multiRoomMessage("Dice rolled on 5 and it's White you earn " + "+" + userBet * 5, getRoom(serverMessage));
                                }
                                else
                                {
                                    userBalance -= userBet * 5;
                                    deleteFile(getUser(serverMessage));
                                    createFile(getUser(serverMessage), userBalance, serverMessage);
                                    multiRoomMessage("Dice rolled on 5 and it's Black you lose " + "-" + userBet * 5, getRoom(serverMessage));
                                }
                            }
                            else
                            {
                                if (x == 1)
                                {
                                    userBalance += userBet * 6;
                                    deleteFile(getUser(serverMessage));
                                    createFile(getUser(serverMessage), userBalance, serverMessage);
                                    multiRoomMessage("Dice rolled on 6 and it's White you earn " + "+" + userBet * 6, getRoom(serverMessage));
                                }
                                else
                                {
                                    userBalance -= userBet * 6;
                                    deleteFile(getUser(serverMessage));
                                    createFile(getUser(serverMessage), userBalance, serverMessage);
                                    multiRoomMessage("Dice rolled on 6 and it's Black you lose " + "-" + userBet * 6, getRoom(serverMessage));
                                }
                            }
                        }
                        else if (userBet == 0)
                        {
                            multiRoomMessage("For this game you have to bet something", getRoom(serverMessage));
                        }
                        else
                        {
                            multiRoomMessage(getUser(serverMessage) + " you don't have enough funds to bet " + userBet, getRoom(serverMessage));
                        }
                    }
                    catch (StringIndexOutOfBoundsException e)
                    {
                        multiRoomMessage("You need to enter a bet.", getRoom(serverMessage));
                    }
                }
                else
                {
                    multiRoomMessage("You need to create an account first.", getRoom(serverMessage));
                }
            }

            // Russian Roulette
            else if(serverMessage.contains(prefix+"russroulette"))
            {
                File file = new File("resources\\"+getUser(serverMessage)+".txt");
                if(file.exists())
                {
                    int userBalance = getUserBalance(serverMessage);
                    int userBet = russRoulette.getBet(russRoulette.format(serverMessage));

                    boolean chance = new Random().nextInt(6) == 0;

                    if(!(userBet > userBalance) && !(userBet == 0) && !(userBet < 0))
                    {
                        if (chance)
                        {
                            userBalance -= userBet * 10;
                            deleteFile(getUser(serverMessage));
                            createFile(getUser(serverMessage), userBalance, serverMessage);
                            multiRoomMessage("You shot yourself, we are going to take x10(" + userBet * 10 + ") of your bet amount to pay for the hospital bills. Better luck next time.", getRoom(serverMessage));
                        }
                        else
                        {
                            userBalance += userBet * 2;
                            deleteFile(getUser(serverMessage));
                            createFile(getUser(serverMessage), userBalance, serverMessage);
                            multiRoomMessage("Congratulations! You won x2(" + userBet * 2 + ") of your bet", getRoom(serverMessage));
                        }
                    }
                    else if(userBet == 0)
                    {
                        multiRoomMessage(getUser(serverMessage) + " if you don't bet anything how are we gonna pay the hospital bills in case something unlucky happens?", getRoom(serverMessage));
                    }
                    else
                    {
                        multiRoomMessage(getUser(serverMessage) + " you don't have enough funds to bet " + userBet, getRoom(serverMessage));
                    }
                }
                else
                {
                    multiRoomMessage("You need to create an account first.", getRoom(serverMessage));
                }
            }

            // Balance command
            else if(serverMessage.contains(prefix+"balance"))
            {
                File file = new File("resources\\"+getUser(serverMessage)+".txt");
                if(file.exists())
                {
                    if(getUserBalance(serverMessage) < 0)
                    {
                        multiRoomMessage(getUser(serverMessage) + ": "+getUserBalance(serverMessage) + " in debt.", getRoom(serverMessage));
                    }
                    else
                    {
                        multiRoomMessage(getUser(serverMessage) + ": "+getUserBalance(serverMessage), getRoom(serverMessage));
                    }
                }
                else
                {
                    multiRoomMessage("You need to create an account first.", getRoom(serverMessage));
                }
            }

            // Help command
            else if(serverMessage.contains(prefix+"help"))
            {
                printCommands(getRoom(serverMessage));
            }
        }

        in.close();
        out.close();
        socket.close();

        System.out.println("Connection Closed!");
    }

    // A method for writing commands
    private static void write(String command, String message)
    {
        String fullMessage = command + " " + message;
        System.out.println(">>> "+ fullMessage);
        out.print(fullMessage + "\r\n");
        out.flush();
    }

    // A method for writing in the room the bot initially joined
    private static void roomMessage(String message)
    {
        String fullMessage = "PRIVMSG" + " #"+room+" :" + "\u00030,1"+message;
        System.out.println(">>> "+ fullMessage);
        out.print(fullMessage + "\r\n");
        out.flush();
    }

    // A method for writing in the room the bot has joined after the initial room
    private static void multiRoomMessage(String message, String room)
    {
        String fullMessage = "PRIVMSG" + " "+room+" :" + "\u00030,1"+message;
        System.out.println(">>> "+ fullMessage);
        out.print(fullMessage + "\r\n");
        out.flush();
    }

    // A method for sending a private message to the user
    private  static void privateMessage(String user, String message)
    {
        String fullMessage = "PRIVMSG" + " "+user+" :" + message;
        System.out.println(">>> "+ fullMessage);
        out.print(fullMessage + "\r\n");
        out.flush();
    }

    // A method to get the current room the bot as been asked for a command in
    private static String getRoom(String serverMessage)
    {
        try
        {
            serverMessage = serverMessage.substring(serverMessage.indexOf(" ") + 1);
            serverMessage = serverMessage.substring(serverMessage.indexOf(" ") + 1);
            serverMessage = serverMessage.substring(0, serverMessage.indexOf(" "));
        }
        catch (StringIndexOutOfBoundsException e)
        {
            roomMessage("Something went wrong!");
        }
        return serverMessage;
    }

    // A method to get the user from the serverMessage
    private static String getUser(String serverMessage)
    {
        String userStart = ":";
        String userEnd = "!";

        String user;
        serverMessage = serverMessage.substring(serverMessage.indexOf(userStart) + 1);
        serverMessage = serverMessage.substring(0, serverMessage.indexOf(userEnd));

        user = serverMessage;

        return user;
    }

    // A method to create a file with a number, this is used for the currency
    private static void createFile(String user, int balance, String serverMessage)
    {
        try
        {
            File file = new File("resources\\"+user+".txt");

            if(!file.exists())
            {
                file.createNewFile();
                PrintWriter pw = new PrintWriter(file);
                pw.println(balance);
                pw.close();
            }
            else
            {
                roomMessage("You already have an account " +user);
                multiRoomMessage("How about the new restaurant called 'Karma'. There's no menu: You get what you deserve.", getRoom(serverMessage));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // A counterpart method to the one above, this one delete that same file
    private static void deleteFile(String user)
    {
        File file = new File("resources\\"+user+".txt");

        if (file.delete())
        {
            System.out.println("Deleted the file: " + file.getName());
        }
        else
        {
            System.out.println("Failed to delete the file.");
        }
    }

    // A method to get the user balance from their file
    private static int getUserBalance(String serverMessage)
    {

        int userBalance = 0;
        try
        {
            File file = new File("resources\\"+getUser(serverMessage)+".txt");
            if(file.exists())
            {
                Scanner myReader = new Scanner(file);
                while (myReader.hasNextLine())
                {
                    userBalance = Integer.parseInt(myReader.nextLine());
                }
                myReader.close();
                return userBalance;
            }
            else
            {
                roomMessage("You need an account first " + getUser(serverMessage));
                multiRoomMessage("How about the new restaurant called 'Karma'. There's no menu: You get what you deserve.", getRoom(serverMessage));
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    // A method that takes a room and prints the commands in that room
    private static void printCommands(String room)
    {
        multiRoomMessage("My prefix is '"+prefix+"', my commands are as follows:", room);
        String[] commands = {
                prefix+"hello\u00030,1 Prints out a hello message to the user.",
                prefix+"serverHello\u00030,1 Prints out a hello message to all the channels in the server.",
                prefix+"time\u00030,1 Gets the time.",
                prefix+"invite\u00030,1 Invites a user to join the current channel.",
                prefix+"join\u00030,1 Send the bot to another channel.",
                prefix+"joke\u00030,1 Makes a random joke.",
                prefix+"help\u00030,1 Displays the command menu.",
                "\u00030,1NOTE: The following commands require you to make an account first using !create.",
                prefix+"coinflip <bet> <Head/Tail>\u00030,1 Flips a coin x1 multiplier.",
                prefix+"rolldice <bet>\u00030,1 Rolls a dice, the number is the multiplier and the.",
                "\u00030,1colours (White/Black) decides if u win or lose.",
                prefix+"russroulette <bet>\u00030,1 '1/6' chance to lose x10 of your bet,",
                "\u00030,1'5/6' to win x2 of your bet.",
                prefix+"balance\u00030,1 Checks your balance."
        };

        for(String s : commands)
        {
            multiRoomMessage("\u00033,1"+s, room);
        }
    }

    // The welcome message prints the commands, this was supposed to say more things but due flooding they were deleted
    private static void welcomeMessage(String room)
    {
        printCommands(room);
    }
}
