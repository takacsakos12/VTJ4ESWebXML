<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:output method="html" indent="yes"/>

<xsl:template match="/">
<html>
<head>
<title>Órarend – VTJ4ES</title>
</head>

<body>
<h2>VTJ4ES Órarend – 2025. I. félév</h2>

<table border="1" cellpadding="6">
    <tr>
        <th>ID</th>
        <th>Típus</th>
        <th>Tárgy</th>
        <th>Nap</th>
        <th>Tól</th>
        <th>Ig</th>
        <th>Helyszín</th>
        <th>Oktató</th>
        <th>Szak</th>
    </tr>

    <xsl:for-each select="VTJ4ES_orarend/ora">
        <tr>
            <td><xsl:value-of select="@id"/></td>
            <td><xsl:value-of select="@tipus"/></td>
            <td><xsl:value-of select="targy"/></td>
            <td><xsl:value-of select="idopont/nap"/></td>
            <td><xsl:value-of select="idopont/tol"/></td>
            <td><xsl:value-of select="idopont/ig"/></td>
            <td><xsl:value-of select="helyszin"/></td>
            <td><xsl:value-of select="oktato"/></td>
            <td><xsl:value-of select="szak"/></td>
        </tr>
    </xsl:for-each>

</table>

</body>
</html>
</xsl:template>

</xsl:stylesheet>
