function goHome() {
    console.log('go home')
    location.href = "/";
}

function msgPrint() {
    if(m!=null)
        alert(m);
}

function loginStatus() {
    if(id) {
        $('#m_id').html(id+"님")
        $('.suc').css('display','block');   // .show();
        $('.bef').css('display','none');    // .hide();
    } else {
        $('.suc').css('display','none');   // .hide();
        $('.bef').css('display','block');    // .show();
    }
}