from flask import * 
from database import*




admin=Blueprint('admin',__name__)

@admin.route('/admin_home')
def admin_home():

	return render_template('admin_home.html')

@admin.route('/admin_viewshop',methods=['post','get'])
def admin_viewshop():
	data={}
	q="select * from shop inner join login using (login_id)"
	res=select(q)
	data['shopview']=res

	if "action" in request.args:
		action=request.args['action']
		sid=request.args['sid']

	else:
		action=None;

	if action=='accept':
		q="update login set usertype='shop' where login_id='%s'"%(sid)
		update(q)
		flash("successfully")

		return redirect(url_for('admin.admin_viewshop'))

	if action=='reject':
		q="update login set usertype='reject' where login_id='%s'"%(sid)
		update(q)
		flash("successfully")
		return redirect(url_for('admin.admin_viewshop'))
	return render_template('admin_viewshop.html',data=data)


@admin.route('/admin_viewshareparts',methods=['post','get'])
def admin_viewshareparts():
	data={}
	q="select * from spareparts inner join login using (login_id)"
	res=select(q)
	data['viewshareparts']=res

	if "action" in request.args:
		action=request.args['action']
		sid=request.args['sid']

	else:
		action=None;

	if action=='accept':
		q="update login set usertype='spareparts' where login_id='%s'"%(sid)
		update(q)
		flash("successfully")

		return redirect(url_for('admin.admin_viewshareparts'))

	if action=='reject':
		q="update login set usertype='reject' where login_id='%s'"%(sid)
		update(q)
		flash("successfully")
		return redirect(url_for('admin.admin_viewshareparts'))
	return render_template('admin_viewshareparts.html',data=data)

@admin.route('/admin_viewproduct')
def admin_viewproduct():
	data={}
	q="select * from product inner join spareparts using (spareparts_id)"
	res=select(q)
	data['viewproduct']=res

	return render_template('admin_viewproduct.html',data=data)


@admin.route('/admin_viewuser')
def admin_viewuser():
	data={}
	q="select * from user inner join login using (login_id)"
	res=select(q)
	data['viewuser']=res

	return render_template('admin_viewuser.html',data=data)

@admin.route('/admin_addservicetype',methods=['post','get'])
def admin_addservicetype():
	data={}
	q="select * from service_type "
	res=select(q)
	data['viewservice_type']=res


	if "mechanic" in request.form:
		
		fname=request.form['fname']
		q="insert into service_type values(null,'%s')"%(fname)
		update(q)
		flash('successfully')
		return redirect(url_for('admin.admin_addservicetype'))

	return render_template('admin_addservicetype.html',data=data)

@admin.route('/admin_viewbikes')
def admin_viewbikes():
	data={}
	q="select * from bike inner join shop using (shop_id)"
	res=select(q)
	data['viewbike']=res

	return render_template('admin_viewbikes.html',data=data)



@admin.route('/admin_viewblog')
def admin_viewblog():
	data={}
	q="select * from blog inner join bike using (bike_id)"
	res=select(q)
	data['viewblog']=res

	return render_template('admin_viewblog.html',data=data)


@admin.route('/admin_viewcomments')
def admin_viewcomments():
	data={}
	q="select * from comments inner join blog using (blog_id)"
	res=select(q)
	data['viewcomments']=res

	return render_template('admin_viewcomments.html',data=data)

@admin.route('/admin_viewbookings')
def admin_viewbookings():
	data={}
	q="SELECT * FROM `order_master` INNER JOIN `order_details` USING (`omaster_id`) INNER JOIN `product` USING (product_id) INNER JOIN `spareparts` ON `order_master`.`spareparts_id`=`spareparts`.`spareparts_id` INNER JOIN `user` USING (user_id)"
	res=select(q)
	data['viewbooking']=res

	return render_template('admin_viewbookings.html',data=data)



@admin.route('/admin_viewpayment')
def admin_viewpayment():
	data={}
	bid=request.args['bid']

	q="select * from payment inner join order_master on payment.payed_for_id=order_master.omaster_id inner join user on payment.user_id=user.user_id where order_master.user_id='%s' and type='product booking'"%(bid)
	res=select(q)
	data['viewpayment']=res

	return render_template('admin_viewpayment.html',data=data)

@admin.route('/admin_viewcomplaint')
def admin_viewcomplaint():
	data={}
	q="select * from complaints inner join user using (user_id)"
	res=select(q)
	data['complaintview']=res
	return render_template('admin_viewcomplaints.html',data=data)

@admin.route('/sendreply',methods=['post','get'])
def sendreply():

	if "sendreply" in request.form:
		cid=request.args['cid']
		reply=request.form['reply']
		q="update complaints set reply='%s' where complaint_id='%s'"%(reply,cid)
		update(q)
		flash('successfully')
		return redirect(url_for('admin.admin_viewcomplaint'))


	return render_template('sendreply.html')