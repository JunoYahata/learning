
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

document.querySelectorAll('.tag_selector').forEach((ts) => {
	ts.addEventListener('click', (e) => {
		if (e.target.dataset.tag == '99') {
			document.querySelectorAll('.task').forEach((task) => {
				task.classList.remove('hidden');
			});
		} else {
			document.querySelectorAll('.task').forEach((task) => {
				task.classList.add('hidden');
			});
			document.querySelectorAll('.tag_' + e.target.dataset.tag).forEach((task) => {
				task.classList.remove('hidden');
			});
		}
	});
});

document.querySelector('.insert_button').addEventListener('click', () => {
	window.parent.postMessage('task/insert-action', '*');
});

document.querySelector('.default_insert_button').addEventListener('click', () => {
	window.parent.postMessage('task/default-insert-action', '*');
});

document.querySelectorAll('.update_button').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		window.parent.postMessage('task/update-action/' + e.target.dataset.uid, '*');
	});
});

document.querySelectorAll('.copy_button').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		window.parent.postMessage('task/insert-action/' + e.target.dataset.uid, '*');
	});
});

document.querySelectorAll('.create_record_button').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		window.parent.postMessage('record/insert-action/' + e.target.dataset.uid, '*');
	});
});

document.querySelectorAll('.record_update_button').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		window.parent.postMessage('record/update-action/' + e.target.dataset.uid, '*');
	});
});

document.querySelectorAll('.record_delete_button').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		window.parent.postMessage('record/delete-action/' + e.target.dataset.uid, '*');
	});
});

const id = url.searchParams.get("id");

if (Boolean(id)) {
	document.querySelector('#mb_' + id).classList.toggle('hidden');
	document.querySelector('#db_' + id).classList.toggle('hidden');
	document.querySelector('#ub_' + id).classList.toggle('hidden');

	window.location.hash = '#ub_' + id;
}
