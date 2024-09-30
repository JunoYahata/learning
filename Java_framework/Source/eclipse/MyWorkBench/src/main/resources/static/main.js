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
const task_id = url.searchParams.get("task_id");

if (Boolean(task_id)) {
	document.querySelector('#task_contents').firstElementChild.src ="task/?id="+task_id;
}

setInterval(function() {
	const time = document.querySelector('.now_day_time');
	const now = new Date();

// 各要素を取得
const year = now.getFullYear();
const month = now.getMonth() + 1; // 月は0から始まるので1を加算
const date = now.getDate();
const hours = now.getHours();
const minutes = now.getMinutes();
const seconds = now.getSeconds();

// 結果を表示
//console.log(`${year}年${month}月${date}日 ${hours}時${minutes}分${seconds}秒`);
	time.textContent = year+"年"+month+"月"+date+"日 "+hours+":"+minutes;

}, 1000);



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



