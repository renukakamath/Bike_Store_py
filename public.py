from flask import *
from database import*


public=Blueprint('public',__name__)

@public.route('/')
def public_home():

	return render_template('public_home.html')

@public.route('/login',methods=['post','get'])
def login():
	if "login" in request.form:
		u=request.form['uname']
		p=request.form['pwd']
		q="select * from login where username='%s' and password='%s'"%(u,p)
		res=select(q)
		if res:
			session['login_id']=res[0]['login_id']
			lid=session['login_id']

			if res[0]['usertype']=="admin":
				return redirect(url_for('admin.admin_home'))
			elif res[0]['usertype']=="shop":
				q="select * from shop where login_id='%s'"%(lid)
				res=select(q)
				if res:
					session['shop_id']=res[0]['shop_id']
					sid=session['shop_id']
				return redirect(url_for('shop.shop_home'))

			elif res[0]['usertype']=="spareparts":
				q="select * from spareparts where login_id='%s'"%(lid)
				res=select(q)
				if res:
					session['spareparts_id']=res[0]['spareparts_id']
					sid=session['spareparts_id']
				return redirect(url_for('spareparts.spareparts_home'))
		


		else:
			flash('invalid username and password')
					
	
					

		
	return render_template('login.html')


@public.route('/shop_registration',methods=['post','get'])	
def shop_registration():
	if "shopreg" in request.form:
		f=request.form['fname']
	
		p=request.form['place']
	
		n=request.form['num']
		e=request.form['email']
		u=request.form['uname']
		pa=request.form['pwd']


		q="select * from login where username='%s' and password='%s'"%(u,pa)
		res=select(q)
		if res:

			flash('already exist')

		else:
			
			q="insert into login values(null,'%s','%s','pending')"%(u,pa)
			id=insert(q)
			q="insert into shop values(null,'%s','%s','%s','%s','%s')"%(id,f,p,n,e)
			insert(q)
			print(q)
			
			flash('successfully')
			return redirect(url_for('public.shop_registration'))

	return render_template('shop_registration.html')

@public.route('/spareParts_registration',methods=['post','get'])	
def spareParts_registration():
	if "cusreg" in request.form:
		f=request.form['fname']
		l=request.form['lname']
	
		p=request.form['place']
	
		n=request.form['num']
		e=request.form['email']
		u=request.form['uname']
		pa=request.form['pwd']


		q="select * from login where username='%s' and password='%s'"%(u,pa)
		res=select(q)
		if res:

			flash('already exist')

		else:
			
			q="insert into login values(null,'%s','%s','spareparts')"%(u,pa)
			id=insert(q)
			q="insert into spareparts values(null,'%s','%s','%s','%s','%s','%s')"%(id,f,l,p,n,e)
			insert(q)
			print(q)
			
			flash('successfully')
			return redirect(url_for('public.spareParts_registration'))

	return render_template('spareParts_registration.html')

		

