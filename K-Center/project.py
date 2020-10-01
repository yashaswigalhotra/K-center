from tkinter import *
from sqlite3 import *
class windowlogin:
    def __init__(self,parent):
       self.root=parent 
       self.lbl1=Label(self.root,text="Username:",font=('Arial',28))
       self.lbl1.place(x=10,y=300)
       self.entryusername=Entry(self.root,font=('Arial',28))
       self.entryusername.place(x=200,y=300)
       self.lbl2 = Label(self.root, text="Password:",font=('Arial',28))
       self.lbl2.place(x=10,y=380)
       self.entrypassword = Entry(self.root, show="*", font=('Arial',28))
       self.entrypassword.place(x=200,y=380)
       self.buttonlogin = Button(self.root, text="Login", font=('Arial',28), command=self.login)
       self.buttonlogin.place(x=10,y=450)
       self.lbl3 = Label(self.root, text="",font=('Arial',28))
       self.lbl3.place(x=10,y=420)
    def login(self):
        unm = self.entryusername.get()
        pas = self.entrypassword.get()
        con = connect('project.sqlite')
        selectquery = "SELECT * FROM project WHERE username='"+unm+"' AND password='"+pas+"';"
        cursor = con.execute(selectquery)
        res = False
        for row in cursor:
            res=True
        con.commit()
        con.close()
        if res==True:
            self.lbl3.configure(text="User Authenticated!")
            toplevel=Toplevel(mainroot)
            win1=window1(mainroot)
            win1 = window1(toplevel)
        else:
             self.lbl3.configure(text="User Unauthenticated!")
class window1:                     #this window is to take input and show take input to open windows 1,2,3
    def __init__(self,parent):
        self.root=parent
        self.root.title("window1")
        self.root.geometry("640x320")
        self.label1=Label(self.root,text="Welcome Rajat Rehani",font=('Arial',16))
        self.label1.place(x=0,y=0)
        self.pulseslist=['मूंग दाल','मसूर','अरहर','काली दाल','छोला','लोबिया','साबूदाना','राजमा','मोठ की दाल']
        self.listboxpulses=Listbox(self.root,selectmode=EXTENDED)
        self.button_insert=Button(self.root,text="delete",command=self.button2_click)
        self.button_insert.place(x=10,y=350)
        self.button_insert.config(width=30, height=5)
        self.button_update=Button(self.root,text="update",command=self.button3_click)
        self.button_update.place(x=350,y=350)
        self.button_update.config(width=30, height=5)
        self.button_show=Button(self.root,text="show",command=self.button4_click)
        self.button_show.place(x=690,y=350)
        self.button_show.config(width=30, height=5)
        for pulses in self.pulseslist:
            self.listboxpulses.insert(END,pulses)
            self.listboxpulses.place(x=20,y=30)
            self.button1=Button(self.root,text='select',font=('Arial',24),command=self.button1_click)
            self.button1.place(x=10,y=250)
            self.label1=Label(self.root,text="",font=('Arial',24))
            self.label1.place(x=10,y=300)
    def button1_click(self):
        mytup1=self.listboxpulses.curselection()
        self.allpulses=""
        for index in mytup1:
            pulses = self.pulseslist[index]
            self.allpulses = self.allpulses +"   "+pulses + "   "
        self.label1.configure(text=self.allpulses)
    def button2_click(self):
        toplevel=Toplevel(mainroot)
        win2=window2(toplevel,self.allpulses)
    def button3_click(self):
        toplevel=Toplevel(mainroot)
        win3=window3(toplevel,self.allpulses)
    def button4_click(self):
        toplevel=Toplevel(mainroot)
        win4=window4(toplevel,self.allpulses)
class window2:
    def __init__(self,parent,allpulses):
        self.root=parent
        self.root.title("delete")
        self.root.geometry("640x320")
        self.lbl3=Label(self.root,text="name of the pulses:",font=('Arial',28))
        self.lbl3.place(x=10,y=40)
        self.entrynameofpulse=Entry(self.root,font=('Arial',28))
        self.entrynameofpulse.place(x=350,y=40)
        self.entrynameofpulse.delete(0,END)
        self.entrynameofpulse.insert(0,allpulses)
        self.lbl4 = Label(self.root, text="quantity to be removed:",font=('Arial',28))
        self.lbl4.place(x=10,y=90)
        self.entryquantity = Entry(self.root,font=('Arial',28))
        self.entryquantity.place(x=350,y=85)
        self.lbl=Label(self.root,text="date of consumption:",font=('Arial',28))
        self.lbl.place(x=10,y=130)
        self.entryquantity = Entry(self.root,font=('Arial',28))
        self.entryquantity.place(x=350,y=130)
        self.buttonsubmit = Button(self.root, text="submit", font=('Arial',28))
        self.buttonsubmit.place(x=20,y=200)
        self.lbl5 = Label(self.root, text="",font=('Arial',28))
        self.lbl5.place(x=10,y=200)
        self.lbl5=Label(self.root, text="quantity left:",font=('Arial',28))
        self.lbl5.place(x=10,y=300)
        self.entryquantity = Entry(self.root,font=('Arial',28))
        self.entryquantity.place(x=260,y=300)
class window3:
    def __init__(self,parent,allpulses):
        self.root=parent
        self.root.title("update")
        self.root.geometry("640x320")
        self.lbl6=Label(self.root,text="name of the pulses:",font=('Arial',28))
        self.lbl6.place(x=10,y=40)
        self.entrynameofpulse=Entry(self.root,font=('Arial',28))
        self.entrynameofpulse.place(x=270,y=40)
        self.entrynameofpulse.delete(0,END)
        self.entrynameofpulse.insert(0,allpulses)
        self.lbl7= Label(self.root, text="quantity:",font=('Arial',28))
        self.lbl7.place(x=10,y=80)
        self.entryquantity = Entry(self.root,font=('Arial',28))
        self.entryquantity.place(x=270,y=80)
        self.lbl9=Label(self.root,text="quantity to be add:",font=('Arial',28))
        self.lbl9.place(x=10,y=120)
        self.entryquantity=Entry(self.root,font=('Arial',28))
        self.entryquantity.place(x=270,y=120)
        self.quantity=self.entryquantity.get()
        self.buttonupdate = Button(self.root, text="submit", font=('Arial',28))
        self.buttonupdate.place(x=20,y=200)
        self.lbl8 = Label(self.root, text="",font=('Arial',28))
        self.lbl8.place(x=10,y=150)
        self.lbl10=Label(self.root,text="total quantity:",font=('Arial',28))
        self.lbl10.place(x=10,y=250)
        self.entryquantity=Entry(self.root,font=('Arial',28))
        self.entryquantity.place(x=260,y=250)
        
class window4:
    def __init__(self,parent,allpulses):
        self.root=parent
        self.root.title("show")
        self.root.geometry("640x320")
        self.lbl11=Label(self.root,text="name of the pulses:",font=('Arial',28))
        self.lbl11.place(x=10,y=40)
        self.entrynameofpulse=Entry(self.root,font=('Arial',28))
        self.entrynameofpulse.place(x=270,y=40)
        self.entrynameofpulse.delete(0,END)
        self.entrynameofpulse.insert(0,allpulses)
        self.lbl12=Label(self.root, text="quantity:",font=('Arial',28))
        self.lbl12.lace(x=10,y=80)
        self.entryquantity = Entry(self.root,font=('Arial',28))
        self.entryquantity.place(x=270,y=80)
        self.buttonsubmit = Button(self.root, text="submit", font=('Arial',28))
        self.buttonsubmit.place(x=20,y=150)
        self.lbl12 = Label(self.root, text="",font=('Arial',28))
        self.lbl12.place(x=10,y=150)
mainroot = Tk()
mainroot.title("login")
mainroot.geometry("1000x800")
photoimageobj1=PhotoImage(file="project 2.gif")
labelbackimage=Label(mainroot,image=photoimageobj1)
labelbackimage.pack()
winlogin= windowlogin(mainroot)
mainroot.mainloop()

