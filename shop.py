from flask import * 
from database import*
import uuid




shop=Blueprint('shop',__name__)

@shop.route('/shop_home')
def shop_home():

	return render_template('shop_home.html')

@shop.route('/shop_updateprofile' ,methods=['post','get'])
def shop_updateprofile():
	data={}
	sid=session['shop_id']
	q="select * from shop inner join login using (login_id) where shop_id='%s'"%(sid)
	res=select(q)
	data['shopview']=res

	if "upset" in request.form:
		lid=session['login_id']
		fname=request.form['fname']
		place=request.form['place']
		phone=request.form['phone']
		email=request.form['email']
		uname=request.form['uname']
		password=request.form['password']
		q="update shop set shop_name='%s' ,place='%s',phone='%s',email='%s' where shop_id='%s'"%(fname,place,phone,email,sid)
		update(q)
		q="update login set username='%s' ,password='%s' where login_id='%s'"%(uname,password,lid)
		update(q)
		flash("successfully")


	return render_template('shop_updateprofile.html',data=data)


@shop.route('/shop_managebike',methods=['post','get'])
def shop_managebike():
	data={}
	sid=session['shop_id']
	q="select * from bike inner join shop using (shop_id) where shop_id='%s'"%(sid)
	res=select(q)
	data['bikeview']=res


	if "action" in request.args:
		action=request.args['action']
		bid=request.args['bid']

	else:
		action=None;

	if action=='delete':
		q="delete from  bike where bike_id='%s'"%(bid)
		delete(q)
		flash("successfully")
		return redirect(url_for('shop.shop_managebike'))

	if action=='update':
		q="select * from bike inner join shop using (shop_id) where bike_id='%s'"%(bid)
		select(q)
		data['bikeupdates']=res
		


	if "upset" in request.form:
		lid=session['login_id']
		fname=request.form['fname']
		lname=request.form['lname']
		phone=request.form['phone']
		color=request.form['color']
		email=request.form['email']
		img=request.files['imh']
		path="static/image/"+str(uuid.uuid4())+img.filename
		img.save(path)
		
		q="update bike set bike='%s' ,model='%s',brand='%s',color='%s',image='%s',amount='%s' where bike_id='%s'"%(fname,lname,phone,color,path,email,bid)
		update(q)
		flash("successfully")
		return redirect(url_for('shop.shop_managebike'))
		



	if "mechanic" in request.form:
		lid=session['login_id']
		fname=request.form['fname']
		lname=request.form['lname']
		phone=request.form['phone']
		color=request.form['color']
		email=request.form['email']
		img=request.files['imh']
		path="static/image/"+str(uuid.uuid4())+img.filename
		img.save(path)
		
		q="insert into bike values(null,'%s','%s','%s','%s','%s','%s','%s')"%(sid,fname,lname,phone,color,path,email)
		insert(q)
		flash("successfully")
		return redirect(url_for('shop.shop_managebike'))


	return render_template('shop_managebike.html',data=data)


@shop.route('/shop_addblog' ,methods=['post','get'])
def shop_addblog():
	data={}
	sid=session['shop_id']
	q="select * from blog inner join bike using (bike_id) "
	res=select(q)
	data['blogview']=res	



	if "shopadd" in request.form:
	
		bid=request.args['bid']
		lname=request.form['lname']
		phone=request.form['phone']
		
		
		q="insert into blog values(null,'%s','%s','%s')"%(bid,lname,phone)
		insert(q)
		flash("successfully")
		return redirect(url_for('shop.shop_addblog'))


	return render_template('shop_addblog.html',data=data)


@shop.route('/shop_viewcomments',methods=['post','get'])
def shop_viewcomments():
	data={}
	bid=request.args['bid']
	
	q="select * from comments inner join blog using (blog_id)where blog_id='%s' "%(bid)
	res=select(q)
	data['commentsview']=res

	return render_template('shop_viewcomments.html',data=data)


@shop.route('/shop_viewbikerequest',methods=['post','get'])
def shop_viewbikerequest():
	data={}
	
	q="select * from bike_request inner join bike using (bike_id) inner join user using (user_id) "
	res=select(q)
	data['requestview']=res


	if "action" in request.args:
		action=request.args['action']
		rid=request.args['rid']

	else:
		action=None

	if action=='accept':
		q="update bike_request set status='Accept' where bike_request_id='%s'"%(rid)
		update(q)
		flash("successfully")
		return redirect(url_for('shop.shop_viewbikerequest'))


	if action=='reject':
		q="update bike_request set status='Reject' where bike_request_id='%s'"%(rid)
		update(q)
		flash("successfully")
		return redirect(url_for('shop.shop_viewbikerequest'))

	return render_template('shop_viewbikerequest.html',data=data)


@shop.route('/shop_viewpayment',methods=['post','get'])
def shop_viewpayment():
	data={}
	rid=request.args['rid']
	q="select * from payment inner join user using (user_id) inner join bike_request on bike_request.bike_request_id=payment.payed_for_id where bike_request.user_id='%s' and type='bike request' "%(rid)
	res=select(q)
	data['paymentview']=res



	return render_template('shop_viewpayment.html',data=data)



