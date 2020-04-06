<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Image Form</title>
    </head>

    <body>
         <form method="post" action="img" enctype="multipart/form-data">
            <fieldset>
                <legend>Image Loader</legend>
                <p>You can add an img with this form</p>

                <label for="name">Name <span class="required">*</span></label>
                <input type="text" id="name" name="name" value="" size="40" maxlength="60" />
                <br />
					
				<label for="file">File <span class="required">*</span></label>
                <input type="file" id="file" name="file" accept=".gif,.jpg,.jpeg,.png" />
                <br />	
				
                <input type="submit" value="Send" class="noLabel" />
                <br />
                
                <p>${res}</p>
                
            </fieldset>
        </form>
    </body>
</html>