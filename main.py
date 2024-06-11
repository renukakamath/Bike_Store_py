from flask import Flask 
from public import public
from admin import admin

from shop import shop

from spareparts import spareparts

from api import api

app=Flask(__name__)

app.secret_key='key'

app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(shop,url_prefix='/shop')
app.register_blueprint(spareparts,url_prefix='/spareparts')
app.register_blueprint(api,url_prefix='/api')


app.run(debug=True,port=5003,host="0.0.0.0")