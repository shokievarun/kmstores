
<html>
<head>
	 <title>Invoice</title>
	<link rel="stylesheet" type="text/css" href="CreatePdf.css">
</head>
<body>
	<div>
		 
		<h3>Invoice</h3>
		<address contenteditable>
        <p>K M Stores</p>
        <p>#12 8th Cross, Ashokpuram<br>Yeshwanthpur, Bengaluru 560022</p>
        <p>Mob: 9448722558,  8884879145</p>
		 <td>Date: </td>
				<td id="date">date</td>
      </address>
		<table border="1">
			<tr>
			   <td>Sl.no</td>
				<td>Item</td>
				<td>Rate</td>
				<td>Quantity</td>
				<td>Price</td>
			</tr>
			<#list invoices as invoice>
            <tr>
			    <td id="slno">${invoice.slNo}</td>
				<td id="item">${invoice.item}</td>
				<td id="rate">${invoice.rate}</td>
				<td id="quantity">${invoice.quantity}</td>
				<td id="price">${invoice.price}</td>
			</tr>
			</#list>
		</table>
	</div>

</body>
</html>