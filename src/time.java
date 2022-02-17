public class time
{
    /*
    This class is used to format the server message for the time command
     */
    public static String getTime(String serverMessage)
    {
        serverMessage = serverMessage.substring(0, serverMessage.length()-7);
        serverMessage = serverMessage.substring(serverMessage.indexOf(" ")+1);
        serverMessage = serverMessage.substring(serverMessage.indexOf(" ")+1);
        serverMessage = serverMessage.substring(serverMessage.indexOf(" ")+1);
        serverMessage = serverMessage.substring(serverMessage.indexOf(" ")+2);
        return serverMessage;
    }
}
