
const SUCCESS_MESSAGE = "ステータスを更新しました";
const FAILURE_MESSAGE = "更新に失敗しました";


/**
 * リクエストされたURL
 */
const url = new URL(window.location.href);
const result = url.searchParams.get("result");

document.querySelectorAll('.update_status').forEach((ol) => {
	ol.addEventListener('click', (e) => {
	document.querySelector('#form_' + e.target.dataset.uid).click();
	});
});


document.querySelector('.insert_button').addEventListener('click', () => {
	window.parent.postMessage('slack/insert-action', '*');
});

document.querySelectorAll('.update_button').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		window.parent.postMessage('slack/update-action/' + e.target.dataset.uid, '*');
	});
});

document.querySelectorAll('.delete_button').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		window.parent.postMessage('slack/delete-action/' + e.target.dataset.uid, '*');
	});
});