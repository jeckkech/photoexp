User:
username: ${user.username}
paypalEmail: ${user.paypalEmail}
totalRevenue: ${user.totalRevenue}
availableRevenue: ${user.availableRevenue}
images:

 <g:each in="${user.images}">
    <img src="data:image/jpeg;charset=utf-8;base64,${it.thumbnail}"/>
 </g:each>

<g:uploadForm name="myUpload" controller="defaultUser" action="addImage">
    <fieldset class="form">
        <input type="file" name="file" />
    </fieldset>
    <fieldset class="buttons">
        <g:submitButton name="upload" class="save" value="Upload" />
    </fieldset>
</g:uploadForm>