
const FAILURE_MESSAGE = "失敗しました";

/**
 * リクエストされたURL
 */
const url = new URL(window.location.href);


document.querySelectorAll('.memo_outline').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		document.querySelector('#mb_' + e.target.dataset.id).classList.toggle('hidden');
		document.querySelector('#db_' + e.target.dataset.id).classList.toggle('hidden');
		document.querySelector('#ub_' + e.target.dataset.id).classList.toggle('hidden');

	});
});

document.querySelectorAll('.tag_selector').forEach((ts) => {
	ts.addEventListener('click', (e) => {
		if (e.target.dataset.tag == '99') {
			document.querySelectorAll('.memo').forEach((memo) => {
				memo.classList.remove('hidden');
			});
		} else {
			document.querySelectorAll('.memo').forEach((memo) => {
				memo.classList.add('hidden');
			});
			document.querySelectorAll('.tag_' + e.target.dataset.tag).forEach((memo) => {
				memo.classList.remove('hidden');
			});
		}
	});
});

document.querySelector('.insert_button').addEventListener('click', () => {
	window.parent.postMessage('memo/insert-action', '*');
});

document.querySelectorAll('.update_button').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		window.parent.postMessage('memo/update-action/' + e.target.dataset.uid, '*');
	});
});


document.querySelectorAll('.delete_button').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		window.parent.postMessage('memo/delete-action/' + e.target.dataset.uid, '*');
	});
});