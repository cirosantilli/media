import java.io.*;
import java.lang.reflect.*;
import java.math.BigInteger;
import java.util.*;

/** Local counterpart for the archived slideshow and scrolling clients.
 * Display requests and responses carry the messaging bytes. The helper uses
 * the archived wire codecs and cryptography with local demo accounts and media.
 */
public class SlideshowServer extends SlideshowReflection {
  static final byte[] EMPTY=new byte[0];
  static final boolean HOLD=Boolean.getBoolean("slideshow.hold");
  static final boolean SCROLLING=Boolean.getBoolean("slideshow.scrolling");
  static final long SLIDESHOW_INTERVAL_MS = Long.getLong("slideshow.intervalMs", 4000L);
  static final String[] SCROLLING_TEXTS = System.getProperty("slideshow.texts", "").split("\n");
  static final String FIRST_USER="smiley", SECOND_USER="karla";
  static int setting(String key)throws Exception {
    return Integer.parseInt(((Properties)call(Class.forName("c.a.e.d"),"a",Properties.class)).getProperty(key));
  }
  static Object iface(String name,InvocationHandler h)throws Exception{return Proxy.newProxyInstance(SlideshowServer.class.getClassLoader(),new Class[]{Class.forName(name)},h);}
  static byte[] bytes(Object o,String n,Object...a)throws Exception{return (byte[])call(o,n,byte[].class,a);}
  static void set(Object o,String n,Object...a)throws Exception{call(o,n,void.class,a);}
  static byte[] drain(Object o)throws Exception{return bytes(o,"a",(Integer)call(o,"a",int.class));}
  static byte[] escaped(byte[] b,boolean end)throws Exception{
    Object w=make("a.a.g.a.f.c");set(w,"a",b);if(end)set(w,"a");return drain(w);
  }
  static Object proxy(final Handler h)throws Exception{
    return Proxy.newProxyInstance(SlideshowServer.class.getClassLoader(),new Class[]{Class.forName("a.a.g.a.k")},
      (o,m,a)->{if(m.getName().equals("a")){if(m.getReturnType()==void.class){h.push((byte[])a[0]);return null;}return h.pull((Integer)a[0]);}return false;});
  }
  interface Handler {void push(byte[]b)throws Exception;byte[]pull(int n)throws Exception;}
  static class Queue implements Handler {
    ByteArrayOutputStream out=new ByteArrayOutputStream();
    public void push(byte[]b)throws Exception{}
    void write(byte[]b)throws Exception{out.write(b);}
    public byte[]pull(int n){byte[]b=out.toByteArray();int k=Math.min(n,b.length);out.reset();out.write(b,k,b.length-k);return Arrays.copyOf(b,k);}
  }
  static class Mailbox {
    final String initial,reply;
    final Map<Long,Object> messages=new LinkedHashMap<>(), uploads=new LinkedHashMap<>();
    final Set<Long> read=new HashSet<>();
    boolean initialized=false, replied=false;
    long nextID=System.currentTimeMillis();
    Mailbox(String initial,String reply){this.initial=initial;this.reply=reply;}
    void snapshot(Session s)throws Exception{
      if(!initialized){long id=nextID++;messages.put(id,message(s,"LOCAL DEMO",initial,id));initialized=true;}
      for(Map.Entry<Long,Object> entry:messages.entrySet())
        set(read.contains(entry.getKey())?s.read:s.unread,"a",entry.getKey(),entry.getValue());
    }
    void receive(Session s,Object upload,byte[] plaintext)throws Exception{
      long uploadID=(Long)call(upload,"d",long.class);
      if(uploads.containsKey(uploadID))return;
      uploads.put(uploadID,upload);
      // Log the archived codec's decoded text, including any case normalization.
      String text=new String(plaintext,"UTF-8").replaceAll("[\\x00-\\x08\\x0B-\\x1F\\x7F-\\x9F]", "\uFFFD");
      System.err.println("MAILBOX: received text ("+plaintext.length+" bytes)\n"+text);
      if(!replied){
        long id=nextID++;messages.put(id,message(s,"LOCAL DEMO REPLY",reply,id));replied=true;
        System.err.println("MAILBOX: queued reply for "+new String(s.username,"UTF-8")+"; reload and log in to retrieve it.");
      }
    }
  }
  static class Session extends Queue {
    Object outer=make("a.a.g.a.f.e"), auth=make("a.a.g.a.f.e");
    Object dh,cipher,authCipher,transfer;
    Object incoming=make("c.a.d.o"), unread=make("c.a.d.o"), read=make("c.a.d.o"), actions=make("c.a.d.l");
    byte[] messageKey="LOCAL-DEMO-KEY-16".getBytes("UTF-8");
    int stage=0;boolean started=false,success=false;
    byte[]username=FIRST_USER.getBytes("UTF-8"), password, shared,challenge="LOCAL DEMO CHALLENGE".getBytes("UTF-8"),salt="LOCAL DEMO SALT".getBytes("UTF-8");
    Queue clear;
    Object actionReplies=make("c.a.d.l");
    Map<String,Mailbox> accounts;
    Mailbox mailbox;
    int receivedCount;
    Session(String pw)throws Exception{
      password=pw.getBytes("UTF-8");
      Properties p=(Properties)call(Class.forName("c.a.e.d"),"a",Properties.class);
      shared=hexBytes(p.getProperty("Authentication.SharedSecret"));
    }
    void sendAuth(byte[]b)throws Exception{write(escaped(b,true));}
    public void push(byte[]b)throws Exception{
      set(outer,"a",b);
      byte[] raw=drain(outer);
      if(stage<3){
        set(auth,"a",raw);
        if(!started && (Integer)call(auth,"a",int.class)>0){
          byte[]magic=bytes(auth,"a",1);if(magic[0]!=0x6e)throw new IOException("Bad auth magic");started=true;
          System.err.println("LOGIN: received login start; sending key-exchange parameters.");
          BigInteger prime=BigInteger.probablePrime(512,new java.security.SecureRandom());
          dh=make("a.a.a.f.e",prime,BigInteger.valueOf(2));
          sendAuth(escaped(concat(escaped(prime.toByteArray(),true),escaped(new byte[]{2},true)),true));
        }
        while((Boolean)call(auth,"a",boolean.class)){
          byte[]f=bytes(auth,"b");
          if(stage==0){Object frames=make("a.a.g.a.f.e");set(frames,"a",f);set(dh,"a",bytes(frames,"b"));sendAuth(escaped(escaped(bytes(dh,"c"),true),true));stage=1;
            System.err.println("LOGIN: received client public key; sending server public key.");}
          else if(stage==1){
            byte[]dhKey=bytes(dh,"d");
            Object dhCipher=make("c.a.d.d.f.a",dhKey);
            byte[]name=bytes(dhCipher,"b",f);
            if(accounts==null){if(!Arrays.equals(name,username))throw new IOException("Wrong demo name");}
            else {
              String account=new String(name,"UTF-8");
              mailbox=accounts.get(account);
              if(mailbox==null)throw new IOException("Unknown demo account");
              username=name;
            }
            byte[]key=bytes(make("c.a.d.d.e.g"),"a",username,password,dhKey);
            cipher=make("c.a.d.d.f.a",key);
            clear=new Queue(){public void push(byte[]data)throws Exception{authClear(data);}};
            authCipher=make("a.a.g.a.l",proxy(clear),cipher);stage=2;
            System.err.println("LOGIN: account received; waiting for encrypted challenge request.");
          }
        }
        if(stage==2){byte[]enc=drain(auth);if(enc.length>0)set(authCipher,"a",enc);flushAuth();}
      }else if(raw.length>0){set(transfer,"a",raw);}
    }
    public byte[]pull(int n){try{if(stage==3&&success){byte[]b=bytes(transfer,"a",4096);if(b.length>0)write(escaped(b,false));}return super.pull(n);}catch(Exception e){throw new RuntimeException(e);}}
    void receiveMessages()throws Exception{
      int count=(Integer)call(incoming,"b",int.class);
      while(receivedCount<count){
        Object message=call(incoming,"b",Class.forName("c.a.d.g"),receivedCount++);
        // Validate the complete upload with the original message codec before
        // storing it and queuing a reply for the next login.
        byte[] plaintext=bytes(make("c.a.d.e.a",messageKey),"b",bytes(message,"a"));
        if(mailbox!=null)mailbox.receive(this,message,plaintext);
      }
      Object action;
      while((action=call(actions,"b",Class.forName("c.a.d.k")))!=null){
        int kind=(Integer)call(action,"b",int.class);
        // The original inbox emits 300 on selection, alongside 200 for an
        // unread message. The editor uses it to display the message; it also
        // reaches this stream. The archived codec defines no reply for 300.
        if(kind==300)continue;
        Object msg=call(action,"a",Class.forName("c.a.d.g"));
        long id=(Long)call(msg,"d",long.class);
        if(mailbox==null)continue;
        if(kind==200&&mailbox.messages.containsKey(id))mailbox.read.add(id);
        else if(kind==100){mailbox.messages.remove(id);mailbox.read.remove(id);}
        else if(kind!=200)throw new IOException("Unsupported mailbox action: "+kind);
        if(!HOLD)set(actionReplies,"a",make("c.a.d.k",kind==200?500:400,msg));
      }
    }
    void statusRecord(ByteArrayOutputStream b,Object m,int kind)throws Exception{
      b.write(bytes(Class.forName("a.a.a.j"),"a",(Long)call(m,"d",long.class)));
      b.write(bytes(Class.forName("a.a.a.j"),"a",8));
      b.write(bytes(Class.forName("a.a.a.j"),"a",(Integer)call(m,"c",int.class)));
      b.write(bytes(Class.forName("a.a.a.j"),"a",kind));
    }
    byte[]statusBytes()throws Exception{
      ByteArrayOutputStream b=new ByteArrayOutputStream();
      for(int kind=0;kind<2;kind++){
        Enumeration<?> en=(Enumeration<?>)call(kind==0?unread:read,"d",Enumeration.class);
        while(en.hasMoreElements())statusRecord(b,en.nextElement(),kind);
      }
      return b.toByteArray();
    }
    ByteArrayOutputStream authInput=new ByteArrayOutputStream();
    int authStage=0;
    void authClear(byte[]data)throws Exception{
      authInput.write(data);byte[]b=authInput.toByteArray();
      if(authStage==0){if(b.length!=1||b[0]!=0x68)throw new IOException("Bad challenge magic");authInput.reset();
        clear.write(concat(bytes(Class.forName("a.a.a.j"),"a",challenge.length),challenge,bytes(Class.forName("a.a.a.j"),"a",salt.length),salt));authStage=1;
        System.err.println("LOGIN: sending challenge; waiting for password proof.");
      }else if(authStage==1&&b.length>=36){
        byte[]expected=bytes(make("a.a.a.f.a.a",username,password,shared),"a",challenge,salt);
        byte[]given=Arrays.copyOfRange(b,4,b.length);success=Arrays.equals(given,expected);
        clear.write(new byte[]{(byte)(success?0x73:0x66)});authStage=2;
        System.err.println("SLIDESHOW: local authentication "+(success?"accepted":"rejected"));
      }
    }
    void flushAuth()throws Exception{
      byte[]b=bytes(authCipher,"a",8192);if(b.length>0)sendAuth(escaped(b,false));
      if(authStage==2&&success){
        if(mailbox!=null)mailbox.snapshot(this);
        final byte[] status=statusBytes(); // Immutable login snapshot; senders drain their queues.
        write(escaped(bytes(Class.forName("a.a.e.h.b"),"a",messageKey,concat(username,password)),true));
        Object mux=make("a.a.g.a.e.e");
        Object receiver=make("LocalSlideshowReceiver",incoming);
        Object empty=Proxy.newProxyInstance(SlideshowServer.class.getClassLoader(),new Class[]{Class.forName("a.a.g.a.a.g")},(o,m,a)->{
          if(m.getReturnType()==byte[].class)return status;if(m.getReturnType()==long.class)return 0L;if(m.getReturnType()==int.class)return 1000000;return null;});
        Object unreadSender=make("c.a.d.d.a.f",unread,false);
        // The older client has no reverse action channel. Its server-only
        // adapter uses the archived action codec and generic list receiver.
        Object[]channels=HOLD?
          new Object[]{receiver,unreadSender,make("c.a.d.d.a.f",read,false),make("LocalActionReceiver",actions),make("a.a.g.a.a.e",empty)}:
          new Object[]{receiver,unreadSender,make("c.a.d.d.a.f",read,false),make("c.a.d.d.b.a",actions),make("c.a.d.d.b.b",actionReplies,true),make("a.a.g.a.a.e",empty)};
        for(int i=0;i<channels.length;i++)set(mux,"a",channels[i],i==channels.length-1?10:(HOLD?9:8)-i,Byte.valueOf((byte)i));
        transfer=make("a.a.g.a.l",mux,cipher);stage=3;
      }
    }
  }
  static byte[]hexBytes(String s){byte[]b=new byte[s.length()/2];for(int i=0;i<b.length;i++)b[i]=(byte)Integer.parseInt(s.substring(i*2,i*2+2),16);return b;}
  static byte[]concat(byte[]...a)throws Exception{ByteArrayOutputStream b=new ByteArrayOutputStream();for(byte[]v:a)b.write(v);return b.toByteArray();}
  // The 376-class client feeds its authentication stream directly into the
  // content transport; it predates the newer reliable-packet wrapper.
  static Object transport(Object stream)throws Exception{return HOLD?stream:make("a.a.g.a.d.b",stream);}
  static byte[] readSlide(String name) throws IOException {
    try (InputStream input = SlideshowServer.class.getResourceAsStream(name)) {
      if (input == null) throw new FileNotFoundException(name);
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      byte[] chunk = new byte[4096];
      for (int length; (length = input.read(chunk)) != -1;) output.write(chunk, 0, length);
      return output.toByteArray();
    }
  }
  static class Wire {
    final Session session;final Object reliable,unstuffer,stuffer;
    final List<byte[]> slides = new ArrayList<>();
    final java.awt.Font scrollingFont;
    int supplied=0,sequence=0;
    Wire(String pw)throws Exception{
      session=new Session(pw);reliable=transport(proxy(session));
      Object sink=iface("c.b.m.c",(o,m,a)->{set(reliable,"a",(byte[])a[0]);return null;});
      unstuffer=make("c.b.h.j",sink);
      Object source=iface("c.b.m.f",(o,m,a)->{
        if(m.getName().equals("b"))return supplied;
        int n=(Integer)a[0];byte[]b=bytes(reliable,"a",n);supplied=b.length;return Arrays.copyOf(b,n);
      });stuffer=make("c.b.i.h",source);
      // Materialize the unchanged renderer's own fallback font in the wire
      // descriptor. CheerpJ 4.3 was reported to fail inside Font.getFont2D
      // when displaying the descriptor with an absent font. This avoids that
      // implicit-font path without inventing a different font/style/size.
      scrollingFont=SCROLLING?(java.awt.Font)call(make("c.b.o.b.b.e",SCROLLING_TEXTS[0],null,null),"b",java.awt.Font.class):null;
      if (!SCROLLING) {
        // The archived Nepal servlet capture was empty. Start with the JAR's
        // own background and cycle through any labeled reconstruction slides.
        slides.add(readSlide("/nepal2.jpg"));
        String extraSlides = System.getProperty("slideshow.extraSlides", "");
        if (!extraSlides.isEmpty()) {
          for (String name : extraSlides.split(",")) slides.add(readSlide(name));
        }
      }
    }
    byte[] exchange(byte[]raw)throws Exception{
      int count=0;
      try(ObjectInputStream in=new ObjectInputStream(new ByteArrayInputStream(raw)){
        protected Class<?>resolveClass(ObjectStreamClass d)throws IOException,ClassNotFoundException{
          if(!d.getName().equals("irf.gnh.qec.wig.a")&&!d.getName().equals("[B"))throw new InvalidClassException("Unexpected request class",d.getName());return super.resolveClass(d);
        }
        protected Class<?>resolveProxyClass(String[]n)throws IOException{throw new InvalidClassException("Proxy request rejected");}
      }){
        while(true){Object req;try{req=in.readObject();}catch(EOFException done){break;}
          if(!req.getClass().getName().equals("irf.gnh.qec.wig.a")||++count>setting("Connection.Request.MaxBundleSize"))throw new IOException("Bad request bundle");
          set(unstuffer,"a",bytes(req,"a"));
        }
      }
      if(count==0)throw new IOException("Empty request");
      session.receiveMessages();
      ByteArrayOutputStream out=new ByteArrayOutputStream();ObjectOutputStream objects=new ObjectOutputStream(out);
      for(int i=0;i<count;i++){
        Object pixels=SCROLLING ? make("irf.gnh.qec.zu.vba.a",SCROLLING_TEXTS[Math.floorMod(sequence, SCROLLING_TEXTS.length)],scrollingFont,null)
            : make("irf.gnh.qec.zu.vba.a", slides.get(Math.floorMod(sequence, slides.size())));
        Object content=make("irf.gnh.qec.zu.c",pixels);
        Object effect = SCROLLING
            ? make("irf.gnh.qec.ztx.c", Integer.getInteger("slideshow.scrollSpeed", 64))
            : make("irf.gnh.qec.ztx.c", SLIDESHOW_INTERVAL_MS);
        Object display=make("irf.gnh.qec.er.a",content,effect,"local-background-"+(sequence++));
        byte[]data=bytes(stuffer,"b",setting("Connection.Request.SecureSize"));set(display,"a",data.length);call(display,"a",int.class,data);objects.writeObject(display);
      }
      objects.close();return out.toByteArray();
    }
  }
  static void activationTest()throws Exception {
    Object listener=make("a.a.i.a.a");
    Properties config=(Properties)call(Class.forName("c.a.e.d"),"a",Properties.class);
    listener.getClass().getMethod("a",String.class).invoke(listener,config.getProperty("KeyListener.KeySequence"));
    // char parameters are invoked explicitly because reflection also sees
    // overloads taking arbitrary Object values.
    char trigger=config.getProperty("KeyListener.StickyKey").charAt(0),clear=config.getProperty("KeyListener.ClearKey").charAt(0);
    listener.getClass().getMethod("a",char.class).invoke(listener,trigger);
    listener.getClass().getMethod("b",char.class).invoke(listener,clear);
    final int[]accepted={0};
    listener.getClass().getMethod("a",Class.forName("a.a.i.a.b")).invoke(listener,iface("a.a.i.a.b",(o,m,a)->{if((Integer)call(a[0],"a",int.class)==1)accepted[0]++;return null;}));
    java.awt.Canvas canvas=new java.awt.Canvas();
    String sequence=System.getProperty("slideshow.activation");
    activate(listener,canvas,clear,trigger,"!"+sequence,HOLD);
    if(accepted[0]!=0)throw new AssertionError("Wrong activation accepted");
    if(HOLD){
      activate(listener,canvas,clear,trigger,sequence,false);
      if(accepted[0]!=0)throw new AssertionError("Hold variant activated without holding the key");
    }
    activate(listener,canvas,clear,trigger,sequence,HOLD);
    if(accepted[0]!=1)throw new AssertionError("Original keyboard activation failed");
    System.out.println("PASS: original keyboard listener rejects wrong sequence and accepts the configured activation.");
  }
  static void key(Object listener,java.awt.Canvas canvas,char ch,boolean pressed)throws Exception {
    java.awt.event.KeyEvent event=new java.awt.event.KeyEvent(canvas,
      pressed?java.awt.event.KeyEvent.KEY_PRESSED:java.awt.event.KeyEvent.KEY_RELEASED,
      0,0,java.awt.event.KeyEvent.VK_UNDEFINED,ch);
    listener.getClass().getMethod(pressed?"keyPressed":"keyReleased",java.awt.event.KeyEvent.class).invoke(listener,event);
  }
  static void activate(Object listener,java.awt.Canvas canvas,char clear,char trigger,String sequence,boolean hold)throws Exception {
    key(listener,canvas,clear,true);key(listener,canvas,clear,false);
    if(hold)key(listener,canvas,trigger,true);
    for(char ch:sequence.toCharArray()){key(listener,canvas,ch,true);key(listener,canvas,ch,false);}
    if(!hold)key(listener,canvas,trigger,true);
    key(listener,canvas,trigger,false);
  }
  static Object message(Session s,String subject,String text,long id)throws Exception {
    Object codec=make("c.a.d.e.a",s.messageKey),msg=make("c.a.d.g");
    set(msg,"a",bytes(codec,"a",text.getBytes("UTF-8")));
    set(msg,"a",(Object)subject);set(msg,"a",id);return msg;
  }
  public static void main(String[]args)throws Exception {
    if(args.length==1&&args[0].equals("validate-response")){
      String line=new BufferedReader(new InputStreamReader(System.in,"UTF-8")).readLine();
      Object sink=iface("c.b.m.c",(o,m,a)->null);
      Object parser=make("c.a.a.b",sink);
      Object decoded=call(parser,"a",Class.forName("[Lc.b.e.b.d;"),Base64.getDecoder().decode(line));
      if(Array.getLength(decoded)!=1)throw new AssertionError("Slideshow response count");
      if (!SCROLLING) {
        Object effect = call(Array.get(decoded, 0), "b", Class.forName("c.b.n.a.a"));
        long interval = (Long) call(effect, "a", long.class);
        if (interval != SLIDESHOW_INTERVAL_MS)
          throw new AssertionError("Incorrect wire slideshow interval: " + interval + " ms");
        System.out.println("PASS: original client decoded the " + interval + " ms slideshow interval.");
      }
      System.out.println("PASS: Python/Java server pipe returns a response accepted by the original client parser.");
      return;
    }
    PrintWriter replies=new PrintWriter(new FileOutputStream(FileDescriptor.out),true);
    System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.err),true));
    BufferedReader input=new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
    String password=new String(Base64.getDecoder().decode(input.readLine()),"UTF-8");
    String initial=new String(Base64.getDecoder().decode(input.readLine()),"UTF-8");
    String reply=new String(Base64.getDecoder().decode(input.readLine()),"UTF-8");
    String initial2=new String(Base64.getDecoder().decode(input.readLine()),"UTF-8");
    String reply2=new String(Base64.getDecoder().decode(input.readLine()),"UTF-8");
    Map<String,Mailbox> accounts=new LinkedHashMap<>();
    accounts.put(FIRST_USER,new Mailbox(initial,reply));accounts.put(SECOND_USER,new Mailbox(initial2,reply2));
    if(args.length==1&&args[0].equals("test")){
      System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out),true));
      activationTest();selfTest();
      for (String account : accounts.keySet()) conversationTest(password,accounts,account);
      return;
    }
    Wire wire=new Wire(password);wire.session.accounts=accounts;replies.println("READY");
    String line;
    while((line=input.readLine())!=null){
      try{
        if(line.equals("RESET")){wire=new Wire(password);wire.session.accounts=accounts;replies.println("READY");continue;}
        if(line.length()>12*1024*1024)throw new IOException("Request too large");
        byte[]response=wire.exchange(Base64.getDecoder().decode(line));
        replies.println("OK "+Base64.getEncoder().encodeToString(response));
      }catch(Exception error){
        error.printStackTrace(System.err);
        replies.println("ERR "+Base64.getEncoder().encodeToString(error.toString().getBytes("UTF-8")));
      }
    }
  }
  static void selfTest()throws Exception{
    Wire wire=new Wire("demodemodemo");Session server=wire.session;
    Object client=make("c.a.d.d.g"),creds=make("c.a.d.d.e.d");
    set(creds,"a",server.username);set(creds,"b",server.password);set(creds,"c",server.shared);set(client,"a",creds);set(client,"b");
    Object params=make("c.a.d.d.c.c"),upload=make("c.a.d.o"),clientInbox=make("c.a.d.o");
    Object msg=make("c.a.d.g");Object messageCodec=make("c.a.d.e.a",server.messageKey);set(msg,"a",bytes(messageCodec,"a","ORIGINAL CLIENT UPLOAD".getBytes("UTF-8")));set(msg,"a",(Object)"Local test");set(msg,"a",1234567L);set(upload,"a",Long.valueOf(1234567),msg);
    set(params,"a",upload);set(params,"b",clientInbox);set(params,"c",make("c.a.d.o"));set(params,"a",make("c.a.d.l"));if(!HOLD)set(params,"b",make("c.a.d.l"));set(params,"a",new byte[0]);set(client,"a",params);
    Object downMsg=make("c.a.d.g");set(downMsg,"a",bytes(messageCodec,"a","ORIGINAL CLIENT DOWNLOAD".getBytes("UTF-8")));set(downMsg,"a",(Object)"Local reply");set(downMsg,"a",1234568L);set(server.unread,"a",Long.valueOf(1234568),downMsg);
    final Object clientReliable=transport(client);final int[]provided={0};
    Object source=iface("c.b.m.f",(o,m,a)->{if(m.getName().equals("b"))return provided[0];int n=(Integer)a[0];byte[]b=bytes(clientReliable,"a",n);provided[0]=b.length;return Arrays.copyOf(b,n);});
    Object sink=iface("c.b.m.c",(o,m,a)->{set(clientReliable,"a",(byte[])a[0]);return null;});
    Object generator=make("c.a.g.b",source,setting("Connection.Request.SecureSize")),parser=make("c.a.a.b",sink);
    Object requests=Array.newInstance(Class.forName("c.b.i.r"),1);Array.set(requests,0,make("c.b.i.m","display","",0));
    int rotationSize = SCROLLING ? SCROLLING_TEXTS.length : wire.slides.size();
    for(int i=0;i<150;i++){
      byte[]up=bytes(generator,"a",requests);
      if(i==0 && System.getProperty("slideshow.test.request")!=null)
        java.nio.file.Files.write(java.nio.file.Paths.get(System.getProperty("slideshow.test.request")),up);
      byte[]down=wire.exchange(up);Object displays=call(parser,"a",Class.forName("[Lc.b.e.b.d;"),down);
      if (SCROLLING && i <= rotationSize) {
        Object display = Array.get(displays, 0);
        Object view = call(display, "a", Class.forName("c.b.o.a.h"));
        String expectedText = SCROLLING_TEXTS[i % rotationSize];
        if (!call(view, "b", String.class).equals(expectedText))
          throw new AssertionError("Incorrect text at this point in the scrolling rotation");
        if (i == 0) scrollingDisplayTest(display, view, expectedText);
      } else if (wire.slides.size() > 1 && i <= rotationSize) {
        slideshowDisplayTest(Array.get(displays, 0), wire.slides.get(i % wire.slides.size()));
      }

      if(i >= rotationSize && (Integer)call(server.incoming,"b",int.class)>0&&(Integer)call(clientInbox,"b",int.class)>0)break;
    }
    if(!server.success)throw new AssertionError("auth failed");
    if((Integer)call(server.incoming,"b",int.class)!=1||(Integer)call(clientInbox,"b",int.class)!=1)throw new AssertionError("transfer failed");
    Object gotUp=call(server.incoming,"b",Class.forName("c.a.d.g"),0),gotDown=call(clientInbox,"b",Class.forName("c.a.d.g"),0);
    if(!new String(bytes(messageCodec,"b",bytes(gotUp,"a")),"UTF-8").equals("ORIGINAL CLIENT UPLOAD"))throw new AssertionError("upload content");
    if(!new String(bytes(messageCodec,"b",bytes(gotDown,"a")),"UTF-8").equals("ORIGINAL CLIENT DOWNLOAD"))throw new AssertionError("download content");
    System.out.println("PASS: original serialized requests/responses, DH/challenge authentication, message-key delivery, encrypted upload and download.");
    if (SCROLLING) System.out.println("PASS: original client decodes all scrolling headlines in order and wraps to the first.");
    if (wire.slides.size() > 1) System.out.println("PASS: original client decodes all slideshow images in order and wraps to the first.");
  }
  static void slideshowDisplayTest(Object display, byte[] expected) throws Exception {
    Object view = call(display, "a", Class.forName("c.b.o.a.h"));
    Field imageField = Class.forName("c.b.o.a.a").getDeclaredField("d");
    imageField.setAccessible(true);
    java.awt.Image image = (java.awt.Image) call(imageField.get(view), "a", java.awt.Image.class);
    javax.swing.ImageIcon loaded = new javax.swing.ImageIcon(image);
    java.awt.image.BufferedImage original = javax.imageio.ImageIO.read(new ByteArrayInputStream(expected));
    int width = original.getWidth(), height = original.getHeight();
    if (loaded.getIconWidth() != width || loaded.getIconHeight() != height)
      throw new AssertionError("Incorrect slide dimensions");
    java.awt.image.BufferedImage decoded = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB);
    java.awt.Graphics2D graphics = decoded.createGraphics();
    loaded.paintIcon(null, graphics, 0, 0);
    graphics.dispose();
    if (decoded.getRGB(width / 2, height / 2) != original.getRGB(width / 2, height / 2))
      throw new AssertionError("Incorrect slide at this point in the rotation");
  }
  static void scrollingDisplayTest(Object display,Object view,String text)throws Exception {
    Object effect=call(display,"b",Class.forName("c.b.n.a.a"));
    if(!view.getClass().getName().equals("c.b.o.a.d") || !effect.getClass().getName().equals("c.b.n.a.b"))
      throw new AssertionError("Wrong scrolling content/effect classes");
    java.awt.Component component=(java.awt.Component)view;
    java.awt.Dimension originalSize=component.getSize();
    java.awt.Font originalDefault=(java.awt.Font)call(make("c.b.o.b.b.e",text,null,null),"b",java.awt.Font.class);
    if(!component.getFont().equals(originalDefault))throw new AssertionError("Wire font differs from original renderer default");
    component.setSize(420,100);
    java.awt.image.BufferedImage canvas=new java.awt.image.BufferedImage(420,100,java.awt.image.BufferedImage.TYPE_INT_ARGB);
    java.awt.Graphics2D graphics=canvas.createGraphics();component.paint(graphics);graphics.dispose();
    int painted=0;for(int y=0;y<100;y++)for(int x=0;x<420;x++)if((canvas.getRGB(x,y)>>>24)!=0)painted++;
    if(painted==0)throw new AssertionError("Original text renderer painted no pixels");
    component.setSize(originalSize);
    // Run the actual scrolling panel's geometry and movement code, without an AWT window.
    Object queue=make("c.b.e.b.a","local-scrolling-check");
    try {
      Object panel=make("c.b.k.a","local-scrolling-check",queue);
      ((java.awt.Component)panel).setSize(420,100);
      Object motion=call(panel,"a",Class.forName("c.b.k.d"),display);
      int before=component.getX();set(motion,"a",1L);set(motion,"b",1601L);
      if(component.getX()>=before)throw new AssertionError("Default original scrolling effect did not move left");
      int speed=(Integer)call(effect,"a",int.class),gap=setting("Content.ScrollingContentBufferSize");
      if(speed!=Integer.getInteger("slideshow.scrollSpeed",64))throw new AssertionError("Incorrect wire scrolling speed");
      long next=((long)originalSize.width+gap)*1600/speed;
      set(motion,"a",1L);set(motion,"b",next);
      if((Boolean)call(panel,"a",boolean.class,motion,gap))throw new AssertionError("Content consumed too early");
      set(motion,"b",next+1601L);
      if(!(Boolean)call(panel,"a",boolean.class,motion,gap))throw new AssertionError("Content queue cannot advance");
      System.out.println("PASS: original scrolling geometry allows the next item after about "+next+" ms (text width "+originalSize.width+", gap "+gap+", speed "+(speed/1.6)+" pixels/s).");
    } finally { set(queue,"finalize"); }
    System.out.println("PASS: original wire decoder creates text and scrolling effects; original text painter draws pixels and original scrolling panel moves them left.");
  }
  static class TestClient {
    final Wire wire;
    final Object client, upload=make("c.a.d.o"), inbox=make("c.a.d.o"), read=make("c.a.d.o");
    final Object actions=make("c.a.d.l"), acknowledgements=make("c.a.d.l");
    final Object generator,parser,requests;
    TestClient(String password,Map<String,Mailbox> accounts,String account)throws Exception{
      if(!HOLD)set(Class.forName("c.a.d.a.h"),"b");
      wire=new Wire(password);wire.session.accounts=accounts;
      client=make("c.a.d.d.g");Object creds=make("c.a.d.d.e.d");
      set(creds,"a",account.getBytes("UTF-8"));set(creds,"b",wire.session.password);set(creds,"c",wire.session.shared);set(client,"a",creds);
      Object params=make("c.a.d.d.c.c");
      set(params,"a",upload);set(params,"b",inbox);set(params,"c",read);
      set(params,"a",actions);if(!HOLD)set(params,"b",acknowledgements);set(params,"a",new byte[0]);set(client,"a",params);
      Object reliable=transport(client);int[] supplied={0};
      Object source=iface("c.b.m.f",(o,m,a)->{if(m.getName().equals("b"))return supplied[0];int n=(Integer)a[0];byte[]v=bytes(reliable,"a",n);supplied[0]=v.length;return Arrays.copyOf(v,n);});
      Object sink=iface("c.b.m.c",(o,m,a)->{set(reliable,"a",(byte[])a[0]);return null;});
      generator=make("c.a.g.b",source,setting("Connection.Request.SecureSize"));parser=make("c.a.a.b",sink);
      requests=Array.newInstance(Class.forName("c.b.i.r"),1);Array.set(requests,0,make("c.b.i.m","display","",0));
    }
    void poll(int n)throws Exception{for(int i=0;i<n;i++)exchange(bytes(generator,"a",requests));}
    void exchange(byte[] request)throws Exception{call(parser,"a",Class.forName("[Lc.b.e.b.d;"),wire.exchange(request));}
    void login()throws Exception{set(client,"b");poll(200);}
    void counts(int unreadCount,int readCount)throws Exception{
      if((Integer)call(inbox,"b",int.class)!=unreadCount||(Integer)call(read,"b",int.class)!=readCount)
        throw new AssertionError("Wrong login mailbox snapshot");
      if(!HOLD){
        Object progress=call(Class.forName("c.a.d.a.h"),"a",Class.forName("c.a.d.a.h"));
        Object totals=call(progress,"c",Class.forName("c.a.d.a.b.f"));
        for(String method:new String[]{"h","k"})if((Integer)call(totals,method,int.class)!=unreadCount)throw new AssertionError("Unread transfer count "+method);
        for(String method:new String[]{"i","l"})if((Integer)call(totals,method,int.class)!=readCount)throw new AssertionError("Read transfer count "+method);
      }
    }
    void action(int kind,Object message)throws Exception{set(actions,"a",make("c.a.d.k",kind,message));}
    void body(Object msg,String expected)throws Exception{
      Object codec=make("c.a.d.e.a",wire.session.messageKey);
      if(!Arrays.equals(bytes(codec,"b",bytes(msg,"a")),bytes(codec,"b",bytes(codec,"a",expected.getBytes("UTF-8")))))
        throw new AssertionError("Wrong account's message body");
    }
  }
  static void conversationTest(String password,Map<String,Mailbox> accounts,String account)throws Exception {
    Mailbox mailbox=accounts.get(account);
    if(mailbox.initialized||!mailbox.messages.isEmpty())throw new AssertionError("Another account changed this mailbox");
    TestClient first=new TestClient(password,accounts,account);
    first.poll(150);
    if(first.wire.session.started||mailbox.initialized)throw new AssertionError("Mailbox initialized before login");
    first.login();first.counts(1,0);
    Object opening=call(first.inbox,"b",Class.forName("c.a.d.g"),0);first.body(opening,mailbox.initial);
    first.action(200,opening); // Same action emitted by the original inbox when opened.
    first.action(300,opening); // Selection also emits the display event onto the action stream.
    first.poll(200);first.counts(1,0);
    if(mailbox.messages.size()!=1||mailbox.read.size()!=1||mailbox.replied)
      throw new AssertionError("Opening a message unexpectedly changed the conversation");
    long uploadID=System.currentTimeMillis()+1000;
    Object answer=message(first.wire.session,"Local answer","Yes. I can get it today.",uploadID);
    set(first.upload,"a",Long.valueOf(uploadID),answer);
    byte[] waiting=bytes(first.generator,"a",first.requests);
    if((Integer)call(first.upload,"b",int.class)!=1)throw new AssertionError("Outbox removed upload before acknowledgement");
    first.exchange(waiting);first.poll(300);first.counts(1,0);
    if((Integer)call(first.upload,"b",int.class)!=0)throw new AssertionError("Acknowledged upload still in Outbox");
    if(mailbox.messages.size()!=2||mailbox.uploads.size()!=1||mailbox.read.size()!=1)throw new AssertionError("Mailbox did not retain upload/read/reply");
    if(!HOLD&&(Integer)call(first.acknowledgements,"c",int.class)!=1)throw new AssertionError("Missing read acknowledgement");

    // Like RESET/page reload: new connection and client, same server mailboxes.
    TestClient second=new TestClient(password,accounts,account);second.login();second.counts(1,1);
    Object reply=call(second.inbox,"b",Class.forName("c.a.d.g"),0);second.body(reply,mailbox.reply);
    second.body(call(second.read,"b",Class.forName("c.a.d.g"),0),mailbox.initial);
    second.action(300,opening);second.action(300,reply);second.poll(200);
    if(mailbox.messages.size()!=2||mailbox.read.size()!=1)
      throw new AssertionError("Display-only action changed stored mail/read state");
    if(!HOLD&&(Integer)call(second.acknowledgements,"c",int.class)!=0)
      throw new AssertionError("Display-only action generated an unsupported acknowledgement");
    // Retrying an upload after reconnect must not create another stored message/reply.
    set(second.upload,"a",Long.valueOf(uploadID),answer);second.poll(200);second.counts(1,1);
    if(mailbox.uploads.size()!=1||mailbox.messages.size()!=2)throw new AssertionError("Duplicate upload/reply across login");
    second.action(100,opening);second.action(100,reply);second.poll(200);
    if(!mailbox.messages.isEmpty()||!mailbox.read.isEmpty())throw new AssertionError("Deletes not retained");
    if(!HOLD&&(Integer)call(second.acknowledgements,"c",int.class)!=2)throw new AssertionError("Missing delete acknowledgements");
    TestClient third=new TestClient(password,accounts,account);third.login();third.counts(0,0);
    if(!mailbox.replied)throw new AssertionError("Reload reset conversation");
    System.out.println("PASS: "+account+" first login unread 1/1, read 0/0; display action 300 accepted without mailbox changes or an invented acknowledgement; acknowledged upload leaves Outbox; reply waits for reload; next login unread 1/1, read 1/1; read/delete state and duplicate-upload protection survive reload; empty mailbox stays empty.");
  }

}

class SlideshowReflection {
  static Class<?> box(Class<?> c){if(c==int.class)return Integer.class;if(c==long.class)return Long.class;if(c==byte.class)return Byte.class;if(c==boolean.class)return Boolean.class;return c;}
  static Object call(Object o,String n,Class<?> r,Object... args)throws Exception {
    Class<?> c=o instanceof Class?(Class<?>)o:o.getClass();
    Method best=null;int bestScore=Integer.MAX_VALUE;
    for(Class<?> t=c;t!=null;t=t.getSuperclass())for(Method m:t.getDeclaredMethods()){
      if(!m.getName().equals(n)||m.getReturnType()!=r||m.getParameterTypes().length!=args.length)continue;
      Class<?>[] ps=m.getParameterTypes();boolean ok=true;int score=0;
      for(int i=0;i<args.length;i++){
        if(args[i]==null){if(ps[i].isPrimitive())ok=false;continue;}
        Class<?> parameter=box(ps[i]);
        if(!parameter.isInstance(args[i])){ok=false;break;}
        // In particular b(int) is indexed lookup, while b(Object) is key lookup.
        // Reflection order differs across these otherwise compatible JARs.
        if(parameter!=args[i].getClass())score+=parameter==Object.class?100:1;
      }
      if(ok&&score<bestScore){best=m;bestScore=score;}
    }
    if(best==null)throw new NoSuchMethodException(c+" "+n+" "+r);
    best.setAccessible(true);
    try{return best.invoke(o instanceof Class?null:o,args);}
    catch(InvocationTargetException e){throw new RuntimeException(c+"."+n,e.getCause());}
  }
  static Object make(String n,Object...args)throws Exception{
    for(Constructor<?> c:Class.forName(n).getConstructors()){
      if(c.getParameterTypes().length!=args.length)continue;
      boolean ok=true;Class<?>[]ps=c.getParameterTypes();
      for(int i=0;i<args.length;i++)if(args[i]!=null&&!ps[i].isInstance(args[i])&&!box(ps[i]).isInstance(args[i]))ok=false;
      if(ok){c.setAccessible(true);return c.newInstance(args);}
    }throw new NoSuchMethodException(n);
  }
  static Object field(String cls,String n)throws Exception{Field f=Class.forName(cls).getDeclaredField(n);f.setAccessible(true);return f.get(null);}
  static String hex(byte[]b){StringBuilder s=new StringBuilder();for(byte v:b)s.append(String.format("%02X",v));return s.toString();}
}
