<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:output method="html" indent="yes"/>

<xsl:template match="/">
<html>
<head>
<title>Hallgatói lista</title>
</head>
<body>
<h2>VTJ4ES – Hallgatói lista</h2>

<table border="1" cellpadding="5">
    <tr>
        <th>ID</th>
        <th>Név</th>
        <th>Születési idő</th>
        <th>Szak</th>
        <th>Átlag</th>
    </tr>

    <xsl:for-each select="hallgatok/hallgato">
        <tr>
            <td><xsl:value-of select="@id"/></td>
            <td><xsl:value-of select="nev"/></td>
            <td><xsl:value-of select="szulido"/></td>
            <td><xsl:value-of select="szak"/></td>
            <td><xsl:value-of select="atlag"/></td>
        </tr>
    </xsl:for-each>

</table>

</body>
</html>
</xsl:template>

</xsl:stylesheet>
