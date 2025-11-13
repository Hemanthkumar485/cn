import java.net.*;
import java.io.*;
import java.rmi.*;
public class slidsender
{
public static void main(String a[])throws Exception
{
ServerSocket ser=new ServerSocket(10);
Socket s=ser.accept();
DataInputStream in=new DataInputStream(System.in);
DataInputStream in1=new DataInputStream(s.getInputStream());
String sbuff[]=new String[8];
PrintStream p;
int sptr=0,sws=8,nf,ano,i;
String ch;
do
{
p=new PrintStream(s.getOutputStream());
System.out.println("Sender");
System.out.print("Enter the no. of frames : ");
nf=Integer.parseInt(in.readLine());
p.println(nf);
if(nf<=sws-1)
{
System.out.println("Enter "+nf+" Messages to be send\n");
for(i=1;i<=nf;i++)
{
PA
GE
sbuff[sptr]=in.readLine(); 1
p.println(sbuff[sptr]);
sptr=++sptr%8;
}
}
else
{
}
sws-=nf;
System.out.print("Acknow
ledgment received");
ano=Integer.parseInt(in1.re
adLine());
System.out.println(" for
"+ano+" frames");
sws+=nf;
System.out.println("The no. of frames
exceeds window size"); break;
System.out.print("\nDo you wants to send some more frames : ");
ch=in.readLine();
p.println(ch);
}
while(ch.equals("yes"));
s.close();
}
}
