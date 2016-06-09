User:
username: ${user.username}
paypalEmail: ${user.paypalEmail}
totalRevenue: ${user.totalRevenue}
availableRevenue: ${user.availableRevenue}
images:

 <g:each in="${user.images}">
    <div style="display:inline"><img src="data:image/jpeg;charset=utf-8;base64,${it.thumbnail}"/> <span>${it.name}</span></div>
 </g:each>

<g:uploadForm name="myUpload" controller="defaultUser" action="submitImages">
    <g:submitButton name="uploadFtp" class="save" value="UploadFtp" />
</g:uploadForm>

<g:uploadForm name="myUpload" controller="defaultUser" action="addImage">
    <fieldset class="form">
        <input type="file" name="file" />
    </fieldset>
    <fieldset class="buttons">
        <g:submitButton name="upload" class="save" value="Upload" />
    </fieldset>
</g:uploadForm>