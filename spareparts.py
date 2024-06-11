from flask import * 
from database import*
import uuid




spareparts=Blueprint('spareparts',__name__)

@spareparts.route('/spareparts_home')
def spareparts_home():

	return render_template('spareparts_home.html')

@spareparts.route('/spareparts_update',methods=['post','get'])
def spareparts_update():
	data={}
	sid=session['spareparts_id']
	q="select * from spareparts inner join login using (login_id) where spareparts_id='%s'"%(sid)
	res=select(q)
	data['shopview']=res

	if "upset" in request.form:
		lid=session['login_id']
		fname=request.form['fname']
		lname=request.form['lname']
		place=request.form['place']
		phone=request.form['phone']
		email=request.form['email']
		uname=request.form['uname']
		password=request.form['password']
		q="update spareparts set fname='%s' ,lname='%s',place='%s',phone='%s',email='%s' where spareparts_id='%s'"%(fname,lname,place,phone,email,sid)
		update(q)
		q="update login set username='%s' ,password='%s' where login_id='%s'"%(lid)
		update(q)
		flash("successfully")


	return render_template('spareparts_update.html',data=data)


@spareparts.route('/spareparts_manageproduct',methods=['post','get'])
def spareparts_manageproduct():
	data={}
	sid=session['spareparts_id']
	q="select * from product inner join spareparts using (spareparts_id) where spareparts_id='%s'"%(sid)
	res=select(q)
	data['viewproduct']=res


	if "action" in request.args:
		action=request.args['action']
		bid=request.args['bid']

	else:
		action=None;

	if action=='delete':
		q="delete from  product where product_id='%s'"%(bid)
		delete(q)
		flash("successfully")
		return redirect(url_for('spareparts.spareparts_manageproduct'))

	if action=='update':
		q="select * from product inner join spareparts using (spareparts_id) where product_id='%s'"%(bid)
		select(q)
		data['bikeupdates']=res
		return redirect(url_for('spareparts.spareparts_manageproduct'))


	if "upset" in request.form:
		lid=session['login_id']
		fname=request.form['fname']
		stock=request.form['stock']
		rate=request.form['rate']
		
		img=request.files['imh']
		path="static/image/"+str(uuid.uuid4())+img.filename
		img.save(path)
		
		q="update product set product_name='%s' ,rate='%s',stock='%s',image='%s' where product_id='%s'"%(fname,stock,rate,path,bid)
		update(q)
		flash("successfully")
		return redirect(url_for('spareparts.spareparts_manageproduct'))
		



	if "mechanic" in request.form:
		sid=session['spareparts_id']
		fname=request.form['fname']
		stock=request.form['stock']
		rate=request.form['rate']
		path="static/image/"+str(uuid.uuid4())+img.filename
		img.save(path)
		
		q="insert into product values(null,'%s','%s','%s','%s','%s')"%(sid,fname,rate,stock,path)
		insert(q)
		flash("successfully")
		return redirect(url_for('spareparts.spareparts_manageproduct'))


	return render_template('spareparts_manageproduct.html',data=data)


@spareparts.route('/spareparts_vieworder',methods=['post','get'])
def spareparts_vieworder():
	data={}
	sid=session['spareparts_id']
	
	q="SELECT * FROM `order_master` INNER JOIN `order_details` USING (`omaster_id`) INNER JOIN `product` USING (product_id) INNER JOIN `spareparts` ON `order_master`.`spareparts_id`=`spareparts`.`spareparts_id` INNER JOIN `user` USING (user_id) where order_master.spareparts_id='%s'"%(sid)
	res=select(q)
	data['viewbooking']=res


	if "action" in request.args:
		action=request.args['action']
		oid=request.args['oid']

	else:
		action=None;

	if action=='accept':
		q="update order_master set status='Accept' where omaster_id='%s'"%(oid)
		update(q)
		flash("successfully")
		return redirect(url_for('spareparts.spareparts_vieworder'))

	if action=='reject':
		q="update order_master set status='Reject' where omaster_id='%s'"%(oid)
		update(q)
		flash("successfully")
		return redirect(url_for('spareparts.spareparts_vieworder'))





	return render_template('spareparts_vieworder.html',data=data)


@spareparts.route('/spareparts_viewpayment',methods=['post','get'])
def spareparts_viewpayment():
	data={}
	sid=session['spareparts_id']
	
	q=" SELECT * FROM `payment` INNER JOIN `order_master` ON `order_master`.`omaster_id`=`payment`.`payed_for_id` inner join user on  payment.user_id=user.user_id WHERE TYPE='product booking' "
	res=select(q)
	data['viewbooking']=res







	return render_template('spareparts_viewpayment.html',data=data)


@spareparts.route('/spareparts_viewservice',methods=['post','get'])
def spareparts_viewservice():
	data={}
	sid=session['spareparts_id']
	
	q=" SELECT * from `service_type` "
	res=select(q)
	data['service']=res


	return render_template('spareparts_viewservice.html',data=data)


@spareparts.route('/spareparts_addservice',methods=['post','get'])
def spareparts_addservice():
	data={}
	sid=session['spareparts_id']
	
	q=" SELECT * from `service`inner join service_type using(service_type_id) "
	res=select(q)
	data['service']=res


	if "mechanic" in request.form:
		fname=request.form['fname']
		phone=request.form['phone']
		rate=request.form['rate']
		sid=request.args['sid']
		q="insert into service values(null,'%s','%s','%s','%s')"%(sid,fname,phone,rate)
		insert(q)
		flash("successfully")
		return redirect(url_for('spareparts.spareparts_addservice'))




	return render_template('spareparts_addservice.html',data=data)




@spareparts.route('/spareparts_addtime',methods=['post','get'])
def spareparts_addtime():
	data={}
	sid=session['spareparts_id']
	
	q=" SELECT * from `timeslot`inner join service using(service_id) "
	res=select(q)
	data['service']=res


	if "mechanic" in request.form:
		fname=request.form['fname']
		phone=request.form['phone']
		tid=request.args['tid']
		
		q="insert into timeslot values(null,'%s','%s','%s')"%(tid,fname,phone)
		insert(q)
		flash("successfully")
		return redirect(url_for('spareparts.spareparts_addtime'))



	return render_template('spareparts_addtime.html',data=data)


@spareparts.route('/spareparts_viewservicerequest',methods=['post','get'])
def spareparts_viewservicerequest():
	data={}
	
	
	q=" SELECT * FROM `service_request` INNER JOIN `timeslot` USING (`timeslot_id`) inner join user using (user_id)"
	res=select(q)
	data['service']=res



	if "action" in request.args:
		action=request.args['action']
		oid=request.args['rid']

	else:
		action=None;

	if action=='accept':
		q="update service_request set status='Accept' where service_request_id='%s'"%(oid)
		update(q)
		flash("successfully")
		return redirect(url_for('spareparts.spareparts_viewservicerequest'))

	if action=='reject':
		q="update service_request set status='Reject' where service_request_id='%s'"%(oid)
		update(q)
		flash("successfully")
		return redirect(url_for('spareparts.spareparts_viewservicerequest'))






	return render_template('spareparts_viewservicerequest.html',data=data)




@spareparts.route('/spareparts_serviceviewpayment',methods=['post','get'])
def spareparts_serviceviewpayment():
	data={}
	sid=session['spareparts_id']
	
	q=" SELECT * FROM `payment` INNER JOIN `service_request` ON `service_request`.`service_request_id`=`payment`.`payed_for_id` inner join user on  payment.user_id=user.user_id WHERE TYPE='service request' "
	res=select(q)
	data['viewbooking']=res







	return render_template('spareparts_serviceviewpayment.html',data=data)






