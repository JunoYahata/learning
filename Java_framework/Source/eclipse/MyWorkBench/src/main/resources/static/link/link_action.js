
const FAILURE_MESSAGE = "失敗しました";

/**
 * リクエストされたURL
 */
const url = new URL(window.location.href);

const result = url.searchParams.get("result");

console.log(result);

if (Boolean(result)) {
	returnResult(result);
	document.querySelector(".result").value = FAILURE_MESSAGE;
}



function returnResult(result) {
	console.log(result);
	console.log(window.parent);
	if (!window.parent) {
		return;
	}
	window.parent.postMessage(result, '*');
}



