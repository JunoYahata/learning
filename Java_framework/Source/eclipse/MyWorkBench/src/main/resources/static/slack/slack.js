
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
			} else {
				dvel.scrollLeft = dvel.scrollLeft - 550;
			}
			rel.classList.remove('hidden');
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
			lel.classList.add('hidden');
		} else {
			if (scrollable - dvel.scrollLeft < 550) {
				dvel.scrollLeft = scrollable;
				ol.classList.add('hidden');
			} else {
				dvel.scrollLeft = dvel.scrollLeft + 550;
			}
			lel.classList.remove('hidden');
		}
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