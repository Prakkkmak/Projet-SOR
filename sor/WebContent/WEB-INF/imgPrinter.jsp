<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Image Printer</title>
    </head>

    <body>
         <form method="post" action="printer">
            <fieldset>
                <legend>Image Printer</legend>
                <p>You can get an image from the database with this form</p>

                <label for="name">Name <span class="required">*</span></label>
                <input type="text" id="name" name="name" value="" size="40" maxlength="60" />
                <br />
				
                <input type="submit" value="Send" class="noLabel" />
                <br />
                
                <p>${res}</p>
                
            </fieldset>
        </form>
    </body>
</html>