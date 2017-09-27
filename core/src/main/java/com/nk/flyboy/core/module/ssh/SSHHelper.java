package com.nk.flyboy.core.module.ssh;

import com.jcraft.jsch.*;

import javax.swing.*;
import java.io.*;

/**
 * Created on 2017/9/27.
 */
public class SSHHelper {

    public static Session connect(String ip,int port,String username,String pwd) throws JSchException {
        Session session=null;

        JSch jSch=new JSch();



        session=jSch.getSession(username,ip,port);
        session.setPassword(pwd);

        session.setUserInfo(new MyUserInfoSet());

        session.connect(2000);

        return session;
    }

    //执行单条cmd命令
    public static String exec(Session session,String command) throws JSchException, IOException {

        ChannelExec channel= (ChannelExec) session.openChannel("exec");

        channel.setCommand(command);

        //channel.setOutputStream(System.out);

        channel.connect();

        byte[] bytes1=new byte[1024];
        System.out.println(channel.getInputStream().available());
        BufferedInputStream inputStream= new BufferedInputStream(channel.getInputStream());

        int len=inputStream.read(bytes1);

        inputStream.close();

        channel.disconnect();

        return new String(bytes1,0,len);
    }




    //ssh 客户端示例
    public static void main(String[] arg) {

        try{
            JSch jsch=new JSch();

            //jsch.setKnownHosts("/home/foo/.ssh/known_hosts");

            String host=null;
            if(arg.length>0){
                host=arg[0];
            }
            else{
                host= JOptionPane.showInputDialog("Enter username@hostname",
                        System.getProperty("user.name")+
                                "@localhost");
            }
            String user=host.substring(0, host.indexOf('@'));
            host=host.substring(host.indexOf('@')+1);

            Session session=jsch.getSession(user, host, 22);

            String passwd = JOptionPane.showInputDialog("Enter password");
            session.setPassword(passwd);

            UserInfo ui = new MyUserInfo(){
                public void showMessage(String message){
                    JOptionPane.showMessageDialog(null, message);
                }
                public boolean promptYesNo(String message){
                    Object[] options={ "yes", "no" };
                    int foo=JOptionPane.showOptionDialog(null,
                            message,
                            "Warning",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE,
                            null, options, options[0]);
                    return foo==0;
                }

                // If password is not given before the invocation of Session#connect(),
                // implement also following methods,
                //   * UserInfo#getPassword(),
                //   * UserInfo#promptPassword(String message) and
                //   * UIKeyboardInteractive#promptKeyboardInteractive()

            };

            session.setUserInfo(ui);

            // It must not be recommended, but if you want to skip host-key check,
            // invoke following,
            // session.setConfig("StrictHostKeyChecking", "no");

            //session.connect();
            session.connect(30000);   // making a connection with timeout.

            Channel channel=session.openChannel("shell");

            // Enable agent-forwarding.
            //((ChannelShell)channel).setAgentForwarding(true);

            channel.setInputStream(System.in);
      /*
      // a hack for MS-DOS prompt on Windows.
      channel.setInputStream(new FilterInputStream(System.in){
          public int read(byte[] b, int off, int len)throws IOException{
            return in.read(b, off, (len>1024?1024:len));
          }
        });
       */

            channel.setOutputStream(System.out);

      /*
      // Choose the pty-type "vt102".
      ((ChannelShell)channel).setPtyType("vt102");
      */

      /*
      // Set environment variable "LANG" as "ja_JP.eucJP".
      ((ChannelShell)channel).setEnv("LANG", "ja_JP.eucJP");
      */

            //channel.connect();
            channel.connect(3*1000);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }


    public static abstract class MyUserInfo
            implements UserInfo, UIKeyboardInteractive {
        public String getPassword(){ return null; }
        public boolean promptYesNo(String str){ return false; }
        public String getPassphrase(){ return null; }
        public boolean promptPassphrase(String message){ return false; }
        public boolean promptPassword(String message){ return false; }
        public void showMessage(String message){ }
        public String[] promptKeyboardInteractive(String destination,
                                                  String name,
                                                  String instruction,
                                                  String[] prompt,
                                                  boolean[] echo){
            return null;
        }
    }

    public static class MyUserInfoSet implements UserInfo {

        @Override
        public String getPassphrase() {
            return null;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public boolean promptPassword(String message) {
            return false;
        }

        @Override
        public boolean promptPassphrase(String message) {
            return false;
        }

        @Override
        public boolean promptYesNo(String message) {
            return true;
        }

        @Override
        public void showMessage(String message) {

        }
    }
}
