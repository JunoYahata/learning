/**
 * リクエストされたURL
 */
const url = new URL(window.location.href);

/**
 * タブ切り替えの処理を追加
 */

document.querySelectorAll('.tab_radio').forEach((tab) => {
	tab.addEventListener('click', (e) => {
		document.querySelectorAll('.contents').forEach((view) => {
			view.classList.add('hidden');
		});
		document.querySelector(e.target.dataset.contents).classList.remove('hidden');
	});
});

/**
 * パラメータからタブ指定をチェックし、対応するタブを表示状態とする
 */

const tab = url.searchParams.get("tab");

if (Boolean(tab)) {
	document.querySelector('#main_contents').classList.add('hidden');
	document.querySelector('#' + tab + '_contents').classList.remove('hidden');
	document.querySelector('#' + tab + '_radio').checked = true;
}






/**
 * リンク操作画面のダイアログ作成
 */

document.querySelectorAll('.update_button').forEach((tab) => {
	tab.addEventListener('click', (e) => {
		document.querySelector('.frame').src = "link/update-action/" + e.target.dataset.uid;
		document.querySelector('.dialog').showModal();
	});
});

document.querySelector('.insert_button').addEventListener('click', () => {
	document.querySelector('.frame').src = "link/insert-action";
	document.querySelector('.dialog').showModal();
});


document.querySelectorAll('.delete_button').forEach((tab) => {
	tab.addEventListener('click', (e) => {
		document.querySelector('.frame').src = "link/delete-action/" + e.target.dataset.uid;
		document.querySelector('.dialog').showModal();
	});
});

function delayDialogClose(time) {
	setTimeout("dialogClose()", time);
}

function dialogClose() {
	var currentTab = '';
	document.querySelectorAll('.tab_radio').forEach((tab) => {
		if (tab.checked) {
			console.log(tab.id);
			currentTab = tab.id.replace('_radio', '');
		}
	});
	document.querySelector('.dialog').close();
	window.location.href = '?tab=' + currentTab;

}

window.addEventListener('message', e => {
	if (e.data == "success") {
		document.querySelector('.dialog').removeChild(document.querySelector('.frame'));
		document.querySelector('#dialog_message').classList.remove('hidden');
		delayDialogClose(2000);
	} else if (e.data == "failure") {
		// 何もしない
	} else {
		document.querySelector('.frame').src = e.data;
		document.querySelector('.dialog').showModal();
	}
});



