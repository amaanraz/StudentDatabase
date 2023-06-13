function openEditForm(studentId) {
    // Show the edit form popup
    document.getElementById("form-"+studentId).style.display = "block";
}

function closeEditForm(studentId) {
    // Hide the edit form popup
    document.getElementById("form-"+studentId).style.display = "none";
}

function openAddForm(){
    document.getElementById("addForm").style.display = "block";
}

function closeAddForm(){
    document.getElementById("addForm").style.display = "none";
}