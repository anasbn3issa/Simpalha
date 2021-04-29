/*************************************************************************************/
// -->Template Name: Bootstrap Press Admin
// -->Author: Themedesigner
// -->Email: niravjoshi87@gmail.com
// -->File: datatable_basic_init
/*************************************************************************************/

/****************************************
 *       Basic Table                   *
 ****************************************/
$("#zero_config").DataTable();

/****************************************
 *       Default Order Table           *
 ****************************************/

$("#default_order").DataTable({
	lengthMenu: [
		[5, 20, 40, -1],
		[5, 20, 40, "All"],
	],
	order: [[3, "desc"]],
});

$("#default_order2").DataTable({
	lengthMenu: [
		[5, 20, 40, -1],
		[5, 20, 40, "All"],
	],
	order: [[3, "desc"]],
});

/****************************************
 *       Multi-column Order Table      *
 ****************************************/
$("#multi_col_order").DataTable({
	columnDefs: [
		{
			targets: [0],
			orderData: [0, 1],
		},
		{
			targets: [1],
			orderData: [1, 0],
		},
		{
			targets: [4],
			orderData: [4, 0],
		},
	],
});
