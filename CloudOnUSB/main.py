import os
import time
import hashlib
from flask import Flask, render_template, request, Response, send_file
from werkzeug import secure_filename
import xml.etree.ElementTree as ET
import xml.dom.minidom as MD

app=Flask(__name__,static_url_path='')

@app.route("/")
def hello():
    return render_template("test.html")

@app.route("/s3/<bucketname>",methods=['PUT'])
def putBucket(bucketname):
    path=os.path.join('s3','storage',bucketname)
    if os.path.exists(path):
        return Response("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Error>\n<Code>BucketAlreadyExists</Code>\n<Message>The specified bucket already exists</Message>\n<BucketName>{0}</BucketName>\n</Error>".format(bucketname),mimetype="text/xml"),409
    else:
        os.mkdir(path)
        return Response()

@app.route("/s3/<bucketname>",methods=['DELETE'])
def deleteBucket(bucketname):
    path=os.path.join('s3','storage',bucketname)
    if not os.path.exists(path):
        return Response("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Error>\n<Code>NoSuchBucket</Code>\n<Message>The specified bucket does not exist</Message>\n<BucketName>{0}</BucketName>\n</Error>".format(bucketname),mimetype="text/xml"),404
    else:
        if os.listdir(path)==[]:
            os.rmdir(path)
            return Response()
        else:
            return Response("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Error>\n<Code>BucketNotEmpty</Code>\n<Message>The specified bucket is not empty</Message>\n<BucketName>{0}</BucketName>\n</Error>".format(bucketname),mimetype="text/xml"),409

@app.route("/s3/<bucketname>",methods=['GET'])
def getBucket(bucketname):
    path=os.path.join('s3','storage',bucketname)
    if not os.path.exists(path):
        return Response("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Error>\n<Code>NoSuchBucket</Code>\n<Message>The specified bucket does not exist</Message>\n<BucketName>{0}</BucketName>\n</Error>".format(bucketname),mimetype="text/xml"),404
    filelist=os.listdir(path)
    lst=ET.Element("ListBucketResult")
    n=ET.SubElement(lst,"Name")
    n.text=bucketname
    for file in filelist:
        c=ET.SubElement(lst,"Contents")
        stat=os.stat(os.path.join(path,file))
        k=ET.SubElement(c,"Key")
        k.text=file
        lm=ET.SubElement(c,"LastModified")
        lm.text=time.ctime(stat.st_mtime)
        s=ET.SubElement(c,"Size")
        s.text=str(stat.st_size)
        f=open(os.path.join(path,file),"rb")
        h=ET.SubElement(c,"ETag")
        h.text=hashlib.md5(f.read()).hexdigest()
        f.close()
    return Response(MD.parseString(ET.tostring(lst)).toprettyxml(),mimetype="text/xml")

@app.route("/s3/<bucketname>",methods=['POST'])
def postFile(bucketname):
    file = list(request.files.values())[0]
    r=Response()
    if file:
        filename = secure_filename(file.filename)
        try:
            file.save(os.path.join('s3','storage',bucketname,filename))
            f=open(os.path.join('s3','storage',bucketname,filename),"rb")
            r.set_etag(hashlib.md5(f.read()).hexdigest())
            f.close()
        except FileNotFoundError:
            return Response("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Error>\n<Code>NoSuchBucket</Code>\n<Message>The specified bucket does not exist</Message>\n<BucketName>{0}</BucketName>\n</Error>".format(bucketname),mimetype="text/xml"),404
        return r

@app.route("/s3/<bucketname>/<filename>",methods=['PUT'])
def putFile(bucketname,filename):
    data=request.data
    r=Response()
    try:
        f=open(os.path.join('s3','storage',bucketname,filename),"wb")
        f.write(data)
        f.close()
        r.set_etag(hashlib.md5(data).hexdigest())
    except FileNotFoundError:
        return Response("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Error>\n<Code>NoSuchBucket</Code>\n<Message>The specified bucket does not exist</Message>\n<BucketName>{0}</BucketName>\n</Error>".format(bucketname),mimetype="text/xml"),404
    return r

@app.route("/s3/<bucketname>/<filename>",methods=['DELETE'])
def deleteFile(bucketname,filename):
    path=os.path.join('s3','storage',bucketname,filename)
    if not os.path.exists(path):
        return Response("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Error>\n<Code>NoSuchKey</Code>\n<Message>The specified resource does not exist</Message>\n<Resource>{0}</Resource>\n</Error>".format("/"+bucketname+"/"+filename),mimetype="text/xml"),404
    else:
        os.remove(path)
        return Response()

@app.route("/s3/<bucketname>/<filename>",methods=['GET'])
def getFile(bucketname,filename):
    path=os.path.join('s3','storage',bucketname,filename)
    if os.path.exists(path):
        return send_file(path)
    else:
        if os.path.exists(os.path.join('s3','storage',bucketname)):
            return Response("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Error>\n<Code>NoSuchKey</Code>\n<Message>The resource you requested does not exist</Message>\n<Resource>{0}</Resource>\n</Error>".format("/"+bucketname+"/"+filename),mimetype="text/xml"),404
        else:
            return Response("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Error>\n<Code>NoSuchBucket</Code>\n<Message>The specified bucket does not exist</Message>\n<BucketName>{0}</BucketName>\n</Error>".format(bucketname),mimetype="text/xml"),404

@app.route("/ec2")
def manageEC2():
    action=request.args.get('Action')
    print(action)
    return Response()
app.run(host="0.0.0.0",port=80,debug=True)