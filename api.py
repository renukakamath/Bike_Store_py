from flask import*
from database import*

api=Blueprint('api',__name__)

@api.route('/logins')
def logins():
	data={}
	u=request.args['username']
	p=request.args['password']
	q="select * from login where username='%s' and password='%s'"%(u,p)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='faild'
	return str(data)



@api.route('/Registration')
def Registration():
	data={}
	
	f=request.args['fname']
	l=request.args['lname']
	c=request.args['city']
	h=request.args['hname']
	s=request.args['street']
	u=request.args['username']
	p=request.args['password']
	q="select * from login where username='%s' and password='%s'"%(u,p)
	res=select(q)
	print(q)
	if res:
		data['status']='already'
	else:
		q="insert into login values(null,'%s','%s','customer')"%(u,p)
		id=insert(q)
		print(q)
		q="insert into user values(null,'%s','%s','%s','%s','%s','%s')"%(id,f,l,c,h,s)
		insert(q)
		print(q)
		
		data['status']='success'
	return str(data)


@api.route('/ViewUserprofile')
def ViewUserprofile():
	data={}
	lid=request.args['lid']
	q="select * from user inner join login using (login_id) where login_id='%s'"%(lid)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	data['method']="ViewUserprofile"
	return str(data)

@api.route('/Userprofile')
def Userprofile():
	data={}
	n=request.args['name']
	p=request.args['place']
	ph=request.args['Phone']
	e=request.args['email']
	ln=request.args['lname']
	lid=request.args['login_id']
	u=request.args['uname']
	pas=request.args['pwd']
	q="update user set fname='%s',lname='%s',place='%s',phone='%s',email='%s' where login_id='%s'"%(n,ln,p,ph,e,lid)
	update(q)
	q="update login set username='%s' ,password='%s' where login_id='%s'"%(u,pas,lid)
	update(q)

	data['status']='success'
	data['method']="Userprofile"
	return str(data)



@api.route('/UserViewshop')
def UserViewshop():
	data={}
	
	q="select * from shop  inner join bike using (shop_id)"
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	data['method']="ViewUserprofile"
	return str(data)



@api.route('/searchmechanic')	
def searchmechanic():
	data={}
	search='%'+request.args['search']+'%'
	q="SELECT * FROM `shop` inner join bike using (shop_id) where shop_name like '%s'"%(search)
	res=select(q)
	print(q)
	if res:
		data['status']="success"
		data['data']=res

	else:
		data['status']="failed"
	
	return str(data)



@api.route('/Userviewbike')
def Userviewbike():
	data={}
	sid=request.args['sid']
	
	q="select * from bike inner join shop using (shop_id) where shop_id='%s'"%(sid)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	data['method']="ViewUserprofile"
	return str(data)


@api.route('/Bikerequest')
def Bikerequest():
	data={}

	bid=request.args['bid']
	lid=request.args['log_id']
	q="insert into bike_request values(null,(select user_id from user where login_id='%s'),'%s',curdate(),'pending')"%(lid,bid)
	insert(q)
	print(q)
	
	data['status']='success'
	data['method']="Bikerequest"
	return str(data)





@api.route('/Viewbikerequest')
def Viewbikerequest():
	data={}
	log_id=request.args['log_id']
	
	q="select * from bike_request inner join bike using (bike_id) inner join user using (user_id) where user_id=(select user_id from user where login_id='%s')"%(log_id)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	data['method']="Viewbikerequest"
	return str(data)


@api.route('/Makepayment')
def Makepayment():
	data={}

	brid=request.args['brid']
	lid=request.args['log_id']
	amount=request.args['amount']
	q="insert into payment values(null,(select user_id from user where login_id='%s'),'%s','bike request','%s',curdate())"%(lid,brid,amount)
	insert(q)
	print(q)
	q="update bike_request set status='Paid' where bike_request_id='%s'"%(brid)
	update(q)
	print(q)
	
	data['status']='success'
	data['method']="Makepayment"
	return str(data)


@api.route('/Userviewblog')
def Userviewblog():
	data={}
	
	
	q="select * from blog inner join bike using (bike_id) "
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	data['method']="Userviewblog"
	return str(data)


@api.route('/Addcomments')	
def Addcomments():
	data={}
	bid=request.args['bid']
	c=request.args['complaint']
	q="insert into `comments` values(null,'%s','%s',curdate())"%(bid,c)
	insert(q)
	print(q)
	data['status']="success"
	data['method']="Addcomments"
	return str(data)

@api.route('/viewcomments')
def viewcomments():
	data={}
	bid=request.args['bid']
	q="select * from comments inner join blog using (blog_id) where blog_id='%s'"%(bid)
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="viewcomments"
	return str(data)


@api.route('/Viewproduct')
def Viewproduct():
	data={}
	
	q="select * from product inner join spareparts using (spareparts_id) "
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="Viewproduct"
	return str(data)


@api.route('/Addcart')	
def Addcart():
	data={}
	quantity=request.args['quantity']
	total=request.args['total']
	pid=request.args['pid']
	amt=request.args['amt']
	spid=request.args['spid']
	login_id=request.args['login_id']
	q="select * from order_master where user_id=(select user_id from user where login_id='%s') and status='pending'"%(login_id)
	res=select(q)
	if res:
		omid=res[0]['omaster_id']
	else:


		q="insert into order_master values(null,(select user_id from user where login_id='%s'),'%s','0',curdate(),'pending')"%(login_id,spid)
		omid=insert(q)

	q="select * from order_details where product_id='%s' and omaster_id='%s'"%(pid,omid)
	res=select(q)
	print(q)

	if res:
			odid=res[0]['odetails_id']	

			q="update order_details set quantity=quantity+'%s' , amount=amount+'%s' where odetails_id='%s'"%(quantity,total,odid)
			update(q)
			print(q)

	else:
		w="insert into order_details values(null,'%s','%s','%s','%s')"%(omid,pid,total,quantity)
		insert(w)


	q="update order_master set total=total+'%s' where omaster_id='%s'"%(total,omid)
	update(q)
	data['status']="success"
	data['method']="Viewproduct"
	return str(data)


		




@api.route('/Viewcart')
def Viewcart():
	data={}
	log_id=request.args['log_id']
	
	q="SELECT * FROM `order_details` INNER JOIN `order_master` USING (`omaster_id`) INNER JOIN `product` USING (`product_id`) INNER JOIN `spareparts` ON `order_master`.`spareparts_id`=`spareparts`.`spareparts_id` INNER JOIN `user` USING (`user_id`) where user_id=(select user_id from user where login_id='%s') "%(log_id)
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="Viewcart"
	return str(data)


@api.route('/Cartpayment')
def Cartpayment():
	data={}

	omid=request.args['omid']
	lid=request.args['log_id']
	amount=request.args['amount']
	q="insert into payment values(null,(select user_id from user where login_id='%s'),'%s','product booking','%s',curdate())"%(lid,omid,amount)
	insert(q)
	print(q)
	q="update order_master set status='Paid' where omaster_id='%s'"%(omid)
	update(q)
	print(q)
	
	data['status']='success'
	data['method']="Cartpayment"
	return str(data)


@api.route('/Userviewservice')
def Userviewservice():
	data={}
	log_id=request.args['log_id']
	
	q="select * from service inner join service_type using (service_type_id)"
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="Userviewservice"
	return str(data)

@api.route('/Viewtimeslot')
def Viewtimeslot():
	data={}
	sid=request.args['sid']
	
	q="select * from timeslot inner join service using (service_id) where service_id='%s'"%(sid)
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="Viewtimeslot"
	return str(data)


@api.route('/ServiceRequest')
def ServiceRequest():
	data={}

	tit=request.args['timeslot']
	lid=request.args['log_id']
	q="insert into service_request values(null,(select user_id from user where login_id='%s'),'%s',curdate(),'pending')"%(lid,tit)
	insert(q)
	print(q)
	
	data['status']='success'
	data['method']="ServiceRequest"
	return str(data)

@api.route('/Viewservicerequest')
def Viewservicerequest():
	data={}
	
	
	q="select * from service_request inner join timeslot using(timeslot_id) inner join service using (service_id) "
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="Viewservicerequest"
	return str(data)


@api.route('/Makepayments')
def Makepayments():
	data={}

	sid=request.args['sid']
	lid=request.args['log_id']
	amount=request.args['amount']
	q="insert into payment values(null,(select user_id from user where login_id='%s'),'%s','service request','%s',curdate())"%(lid,sid,amount)
	insert(q)
	print(q)
	q="update service_request set status='Paid' where service_request_id='%s'"%(sid)
	update(q)
	print(q)
	
	data['status']='success'
	data['method']="Makepayments"
	return str(data)


@api.route('/Addvehicle')	
def Addvehicle():
	data={}
	vehicle=request.args['vehicle']
	lid=request.args['login_id']
	model=request.args['model']
	brand=request.args['brand']
	color=request.args['color']

	q="insert into `vehicle_details` values(null,(select user_id from user where login_id='%s'),'%s','%s','%s','%s')"%(lid,vehicle,model,brand,color)
	insert(q)
	print(q)
	data['status']="success"
	data['method']="Addvehicle"
	return str(data)

@api.route('/Viewvehicle')
def Viewvehicle():
	data={}
	lid=request.args['login_id']


	q="select * from vehicle_details where user_id=(select user_id from user where login_id='%s')"%(lid)
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="Viewvehicle"
	return str(data)


@api.route('/Addrent')	
def Addrent():
	data={}
	rent=request.args['rent']
	lid=request.args['login_id']
	vid=request.args['vid']
	
	q="insert into `rent_amount` values(null,'%s','%s',curdate())"%(vid,rent)
	insert(q)
	print(q)
	data['status']="success"
	data['method']="Addrent"
	return str(data)

@api.route('/viewrent')
def viewrent():
	data={}
	vid=request.args['vid']


	q="select * from rent_amount where vehicle_id='%s'"%(vid)
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="viewrent"
	return str(data)


@api.route('/Viewothervehicle')
def Viewothervehicle():
	data={}
	lid=request.args['log_id']


	q="select * from vehicle_details INNER JOIN `rent_amount` USING (vehicle_id) where user_id=(select user_id from user where login_id!='%s')"%(lid)
	res=select(q)
	print(q)
	data['data']=res
	data['status']="success"
	data['method']="Viewothervehicle"
	return str(data)


	


@api.route('/RentRequest')
def RentRequest():
	data={}

	rid=request.args['rid']
	lid=request.args['log_id']
	q="insert into rent_request values(null,'%s',(select user_id from user where login_id='%s'),curdate(),'pending')"%(rid,lid)
	insert(q)
	print(q)
	
	data['status']='success'
	data['method']="RentRequest"
	return str(data)


@api.route('/Rentmakepayment')
def Rentmakepayment():
	data={}

	rid=request.args['rid']
	lid=request.args['log_id']
	amount=request.args['amount']
	q="insert into payment values(null,(select user_id from user where login_id='%s'),'%s','Rent request','%s',curdate())"%(lid,rid,amount)
	insert(q)
	print(q)
	q="update rent_request set status='Paid' where rent_request_id='%s'"%(rid)
	update(q)
	print(q)
	
	data['status']='success'
	data['method']="Rentmakepayment"
	return str(data)


@api.route('/viewrentrequest')
def viewrentrequest():
	data={}

	login_id=request.args['login_id']
	


	q="select * from rent_request INNER JOIN `rent_amount` USING (rent_id) inner join user using(user_id) where user_id=(select user_id from user where login_id='%s')"%(login_id)
	res=select(q)
	print(q)
	data['data']=res
	data['status']="success"
	data['method']="viewrentrequest"
	return str(data)


	


@api.route('/viewpayment')
def viewpayment():
	data={}

	login_id=request.args['login_id']
	reqid=request.args['reqid']
	


	q="select * from payment INNER JOIN `rent_request` on payment.payed_for_id=rent_request.rent_request_id  where payment.user_id=(select user_id from user where login_id='%s') and rent_request_id='%s' and type='Rent request'"%(login_id,reqid)
	res=select(q)
	print(q)
	data['data']=res
	data['status']="success"
	data['method']="viewpayment"
	return str(data)



@api.route('/complaint')	
def complaint():
	data={}
	lid=request.args['lid']
	c=request.args['complaint']
	q="insert into `complaints` values(null,(select user_id from user where login_id='%s'),'%s','pending',curdate(),curtime())"%(lid,c)
	insert(q)
	print(q)
	data['status']="success"
	data['method']="complaint"
	return str(data)

@api.route('/viewcomplaints')
def viewcomplaints():
	data={}
	lid=request.args['lid']
	q="select * from complaints inner join user using (user_id) where login_id='%s'"%(lid)
	res=select(q)
	data['data']=res
	data['status']="success"
	data['method']="viewcomplaints"
	return str(data)



@api.route('/Viewbikesimg')
def Viewbikesimg():
	data={}

	bid=request.args['bid']
	


	q="select * from bikeimage where bike_id='%s'"%(bid)#change tablename=bikeimage and fieldname=images
	res=select(q)
	print(q)
	data['data']=res
	data['status']="success"
	data['method']="Viewbikesimg"
	return str(data)













