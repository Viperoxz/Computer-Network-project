package services;

import server.SendMail;

public class GuideTable {
    private static String content =
            """
                            <!DOCTYPE html>
                            <html lang="en">
                            <head>
                                <meta charset="UTF-8">
                                <title>%s</title>
                                <style>
                                    body {
                                        font-family: Arial, sans-serif;
                                        background-color: #ffffff; 
                                    }
                                    .container {
                                        width: 50%;
                                        margin: 50px auto;
                                        padding: 20px;
                                        border: 2px solid #03667a; 
                                        border-radius: 8px;
                                    }
                                    h2 {
                                        text-align: center;
                                        background-color: #03737a; 
                                        color: #ffffff; 
                                        padding: 10px; 
                                        border-radius: 5px; 
                                    }
                                    p {
                                        text-align: center;
                                        color: #000000; 
                                    }
                                    table {
                                        border-collapse: collapse;
                                        width: 100%;
                                        margin-top: 20px;
                                    }
                                    th, td {
                                        border: 1px solid black;
                                        padding: 8px;
                                        text-align: left;
                                        corlor: #00000;
                                    }
                                    .table-title{
                                        background-color : #03737a;
                                        color: #ffffff; 
                                    }
                                   
                                </style>
                            </head>
                            <body>
                                <div class="container">
                                    <h2>Remote Control PC by Email</h2>
                                    <p class="content">Sending greetings from <b>Group 5 - TNT1 2022</b>, University of Science, HCMUS.<br>
                                    Welcome to our final project for <b>Computer Networking</b> course.<br>
                                    This is a list of requests that you can use to control others' PC. Hope you enjoy it!</p>
                                </div>
                                <table border="1">
                                    <tr class="table-title">
                                        <th>No</th>
                                        <th>Type</th>
                                        <th>Command</th>
                                        <th>Description</th>
                                        <th>Example</th>
                                    </tr>
                                                            <tr>
                                                                <td>1</td>
                                                                <td>GUIDE</td>
                                                                <td>help</td>
                                                                <td>Display the list of commands along with their descriptions</td>
                                                                <td>help</td>
                                                            </tr>
                                                            <tr>
                                                                <td>2</td>
                                                                <td>SCREENSHOT</td>
                                                                <td>screenshot</td>
                                                                <td>Capture a screenshot of the user’s PC</td>
                                                                <td>screenshot</td>
                                                            </tr>
                                                            <tr>
                                                                <td>3</td>
                                                                <td rowspan="4">CONTROL PC</td>
                                                                <td>shutdown</td>
                                                                <td>Shutdown the user’s PC</td>
                                                                <td>shutdown</td>
                                                            </tr>
                                                            <tr>
                                                                <td>4</td>
                                                                <td>restart</td>
                                                                <td>Restart the user’s PC</td>
                                                                <td>restart</td>
                                                            </tr>
                                                            <tr>
                                                                <td>5</td>
                                                                <td>logout</td>
                                                                <td>Logout the user’s PC</td>
                                                                <td>logout</td>
                                                            </tr>
                                                            <tr>
                                                                <td>6</td>
                                                                <td>sleep</td>
                                                                <td>Sleep the user’s PC</td>
                                                                <td>sleep</td>
                                                            </tr>
                                                            <tr>
                                                                <td>7</td>
                                                                <td rowspan="2">KEYLOGGER</td>
                                                                <td>startkeylogger</td>
                                                                <td>Start the process of capturing keystrokes typed into user’s computer</td>
                                                                <td>startkeylogger</td>
                                                            </tr>
                                                            <tr>
                                                                <td>8</td>
                                                                <td>stopkeylogger</td>
                                                                <td>Stop the process of capturing keystrokes typed into user’s computer</td>
                                                                <td>stopkeylogger</td>
                                                            </tr>
                                                            <tr>
                                                                <td>9</td>
                                                                <td rowspan="3">PROCESS</td>
                                                                <td>listprocess</td>
                                                                <td>Get the list of processes currently running on the user’s devices</td>
                                                                <td>listprocess</td>
                                                            </tr>
                                                            <tr>
                                                                <td>10</td>
                                                                <td>startapp&&amp;&lt;app_name&gt;</td>
                                                                <td>Open a specified application</td>
                                                                <td>startapp&amp;&amp;mspaint.exe</td>
                                                            </tr>
                                                            <tr>
                                                                <td>11</td>
                                                                <td>stopapp&&amp;&lt;app_name&gt;</td>
                                                                <td>Close a specified application</td>
                                                                <td>stopapp&amp;&amp;mspaint.exe</td>
                                                            </tr>
                                                           
                                                            <tr>
                                                                <td>12</td>
                                                                <td rowspan="2">FILE</td>
                                                                <td>getfile[1]&&amp;&lt;file_name&gt;</td>
                                                                <td>Get a specified file by filename</td>
                                                                <td>getfile[1]&amp;&amp;mmt.txt</td>
                                                            </tr>
                                                           
                                                            <tr>
                                                                <td>13</td>
                                                                <td>getfile[2]&&amp;&lt;file_path&gt;</td>
                                                                <td>Get a specified file by path to the file</td>
                                                                <td>getfile[2]&amp;&amp;D:\\\\IT\\\\mmt.txt</td>
                                                            </tr>
                                                            <tr>
                                                                <td>14</td>
                                                                <td rowspan="2">DIRECTORY</td>
                                                                <td>exploredirectory&&amp;&lt;dir_path&gt;</td>
                                                                <td>Display entire specified directory as a tree</td>
                                                                <td>exploredirectory&amp;&amp;D:\\\\IT</td>
                                                            </tr>
                                                            <tr>
                                                                <td>15</td>
                                                                <td>exploredrive&&amp;&lt;drive&gt;</td>
                                                                <td>Display entire contents of specified drive</td>
                                                                <td>exploredrive&amp;&amp;D</td>
                                                            </tr>
                                                            <tr>
                                                                <td>16</td>
                                                                <td>APP</td>
                                                                <td>listapp</td>
                                                                <td>Get the list of applications currently running on the user’s devices</td>
                                                                <td>listapp</td>
                                                            </tr>
                                </table>
                            </body>
                            </html>                       
                    """;

    public static void requestGuide(String from){
        System.out.println("help");
        SendMail.serversendEmail(from, "Reply for request: Help", "",
                content);
    }
}