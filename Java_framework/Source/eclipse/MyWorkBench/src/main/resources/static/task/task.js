
const FAILURE_MESSAGE = "失敗しました";

/**
 * リクエストされたURL
 */
const url = new URL(window.location.href);


document.querySelectorAll('.task_outline').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		document.querySelector('#mb_' + e.target.dataset.id).classList.toggle('hidden');
		document.querySelector('#db_' + e.target.dataset.id).classList.toggle('hidden');
		document.querySelector('#ub_' + e.target.dataset.id).classList.toggle('hidden');

	});
});


document.querySelector('.insert_button').addEventListener('click', () => {
	window.parent.postMessage('task/insert-action', '*');
});

document.querySelector('.update_button').addEventListener('click', (e) => {
	window.parent.postMessage('task/update-action/' + e.target.dataset.uid, '*');
});
