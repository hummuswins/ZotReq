// "As of jQuery 1.5, the success setting can accept an array of functions. Each function will be called in turn."
let container = document.getElementById('mynetwork');
$.ajax({
	url: 'courseGraph',
	success: [(data) => parseData(data)]
});

let i = 0;

function addForm(node) {
	$(".form").append("<div class=\"form-check\">\n" +
		"        <input class=\"form-check-input\" type=\"checkbox\" value=\"\" id=\"defaultCheck" + i + "\">\n" +
		"        <label class=\"form-check-label\" for=\"defaultCheck" + i++ + "\">\n" +
		"            " + node.id +
		"        </label>\n" +
		"    </div>");
}

function parseData(graphData) {
	let parsedData = vis.network.convertDot(graphData);

	let data = {
		nodes: parsedData.nodes,
		edges: parsedData.edges
	};
	parsedData.nodes.forEach(addForm);

	let options = parsedData.options;

	// you can extend the options like a normal JSON variable:
	options.nodes = {
		color: 'red'
	};

	options.physics = {
		solver: 'forceAtlas2Based'
	};

	// create a network
	let network = new vis.Network(container, data, options);
	network.on('click', function (params) {
		$("#eventlog").append(JSON.stringify(params));

	});
}

$(".form").change(function () {
	let str = "";
	$(".form input:checked").each(function () {
		console.log("label[for=\'" + $(this)[0].id + "\']");
		str += $("label[for=\'" + $(this)[0].id + "\']").text();
	});
	console.log(str);
});