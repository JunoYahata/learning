
const SUCCESS_MESSAGE = "ステータスを更新しました";
const FAILURE_MESSAGE = "更新に失敗しました";


/**
 * リクエストされたURL
 */
const url = new URL(window.location.href);
const result = url.searchParams.get("result");

document.querySelectorAll('.left_button').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		const dvel = document.querySelector(e.target.dataset.idh + 'status');
		const rel = document.querySelector(e.target.dataset.idh + 'right');
		const scrollable = dvel.scrollWidth - 550;
		if (scrollable < 0) {
			ol.classList.add('hidden');
			rel.classList.add('hidden');
		} else {
			if (dvel.scrollLeft < 550) {
				dvel.scrollLeft = 0;
				ol.classList.add('hidden');
				rel.classList.remove('hidden');
			} else {
				dvel.scrollLeft = dvel.scrollLeft - 550;
				rel.classList.remove('hidden');
			}
		}

	});
});

document.querySelectorAll('.right_button').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		const dvel = document.querySelector(e.target.dataset.idh + 'status');
		const lel = document.querySelector(e.target.dataset.idh + 'left');
		const scrollable = dvel.scrollWidth - 550;
		if (scrollable < 0) {
			ol.classList.add('hidden');
			rel.classList.add('hidden');
		} else {
			if (scrollable - dvel.scrollLeft < 550) {
				dvel.scrollLeft = scrollable;
				ol.classList.add('hidden');
				lel.classList.remove('hidden');
			} else {
				dvel.scrollLeft = dvel.scrollLeft + 550;
				lel.classList.remove('hidden');
			}
		}
	});
});

document.querySelector('.memo.insert_button').addEventListener('click', () => {
	window.parent.postMessage('memo/insert-action', '*');
});

document.querySelectorAll('.memo.update_button').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		window.parent.postMessage('memo/update-action/' + e.target.dataset.uid, '*');
	});
});

document.querySelectorAll('.memo.delete_button').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		window.parent.postMessage('memo/delete-action/' + e.target.dataset.uid, '*');
	});
});

document.querySelector('.task.insert_button').addEventListener('click', () => {
	window.parent.postMessage('task/insert-action', '*');
});

document.querySelectorAll('.task.update_button').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		window.parent.postMessage('task/update-action/' + e.target.dataset.uid, '*');
	});
});

document.querySelectorAll('.task.copy_button').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		window.parent.postMessage('task/insert-action/' + e.target.dataset.uid, '*');
	});
});

document.querySelectorAll('.record.update_button').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		window.parent.postMessage('record/update-action/' + e.target.dataset.uid, '*');
	});
});

document.querySelectorAll('.record.delete_button').forEach((ol) => {
	ol.addEventListener('click', (e) => {
		window.parent.postMessage('record/delete-action/' + e.target.dataset.uid, '*');
	});
});