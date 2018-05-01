<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<!-- <xsl:import href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"/> -->
  <xsl:template match="/">
    <html>
      <head>
        <title>Lab4</title>
        <!-- <meta charset="utf-8"> -->
        <!-- <meta name="viewport" content="width=device-width, initial-scale=1"> -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"></link>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
      </head>

      <body>
        <h2 class="page-header text-primary">My CD Collection</h2>
        <div class="alert alert-dark alert-dismissible fade show">
          <!-- <button type="button" class="close" data-dismiss="alert">&times;</button> -->
          <strong>Note:</strong> The CDs from the year 1987 are in danger!
        </div>
        <table class="table table-striped">
        <thead>
        <tr>
          <th>Title</th>
          <th>Artist</th>  
          <th>Year</th> 
        </tr>
        </thead>
        <tbody>
        <xsl:for-each select="catalog/cd">    
          <xsl:choose> 
            <xsl:when test="year=1987">
              <tr class="text-danger">
                <td><xsl:value-of select="title"/></td>
                <td><xsl:value-of select="artist"/></td>     
                <td><xsl:value-of select="year"/></td>      
              </tr>
            </xsl:when>
            <xsl:otherwise>
              <tr>
                <td><xsl:value-of select="title"/></td>
                <td><xsl:value-of select="artist"/></td>  
                <td><xsl:value-of select="year"/></td>     
              </tr>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:for-each>
        </tbody>
        </table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>

