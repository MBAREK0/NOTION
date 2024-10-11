<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.MBAREK0.web.util.DateUtil" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Create Task</title>

  <script src="https://cdn.jsdelivr.net/gh/alpinejs/alpine@v2.x.x/dist/alpine.min.js" defer></script>

  <link href="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.css" rel="stylesheet" />

  <link href="https://cdn.jsdelivr.net/npm/tailwindcss@3.3.3/dist/tailwind.min.css" rel="stylesheet">
  <!-- Select2 CSS -->
  <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet"/>

  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">

  <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
</head>
<body>

<jsp:include page="../layout/aside.jsp" />
<div class="p-4 sm:ml-64">
  <div class="p-4 rounded-lg dark:border-gray-700">
    <div class="mb-4 mt-10">
      <%-- Content --%>
      <form class="max-w-2xl mx-auto" action="<%= request.getContextPath() %>/tasks?action=edit" method="post">
        <h1 class="text-3xl font-bold text-gray-800 mb-4">
        Update Task
        </h1>
        <input type="hidden" name="id" value="${task.id}" />
        <div class="relative z-0 w-full mb-5 group">
          <label for="title" class="block mb-2 text-sm font-medium text-gray-900">Enter the title of the task</label>
          <input type="text" value="${task.title}" name="title" id="title" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full  p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder=" " required />
        </div>

        <div class="relative z-0 w-full mb-5 group">
          <label for="description" class="block mb-2 text-sm font-medium text-gray-900">Enter the Description of the task</label>
          <textarea name="description"  id="description" rows="2" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder=" " required>
            ${task.description}
          </textarea>
        </div>

        <div class="relative z-0 w-full mb-5 group">
          <label for="task_status" class="sr-only">Task Status </label>
          <select id="task_status" name="status" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">

            <option value="${task.status}">${task.status}</option>
            <c:if test="${task.status != 'overdue'}">
              <c:forEach var="status" items="${statusList}">
                <c:if test="${status != task.status}">
                  <option value="${status}">${status}</option>
                </c:if>
              </c:forEach>
            </c:if>
          </select>
        </div>

        <div class="grid md:grid-cols-2 md:gap-6">
          <div class="relative z-0 w-full mb-5 group">
            <label for="tag-multiple" class="block mb-2 text-sm font-medium text-gray-900">Select the task tags</label>
            <select class="selectTow w-full" name="tags" id="tag-multiple" multiple>
                <c:forEach var="tag" items="${tags}">
                    <c:if test="${task.tags.contains(tag)}">
                        <option value="${tag.id}" selected>${tag.name}</option>
                    </c:if>
                    <c:if test="${!task.tags.contains(tag)}">
                        <option value="${tag.id}">${tag.name}</option>
                    </c:if>
                </c:forEach>
            </select>
          </div>

          <c:if test="${role == 'manager'}">
            <div class="relative z-0 w-full mb-5 group">
              <label for="states-single" class="block mb-2 text-sm font-medium text-gray-900">Select users to assign the task</label>
              <select class=" selectOne w-full" name="userId" id="states-single">
                <option value="${task.user.id}">${task.user.username}</option>
                <c:forEach var="user" items="${users}">
                  <c:if test="${user.id != task.user.id}">
                    <option value="${user.id}">${user.username}</option>
                  </c:if>
                </c:forEach>
              </select>
            </div>
          </c:if>
          <c:if test="${role == 'user'}">
            <div class="relative z-0 w-full mb-5 group">
              <label for="states-single2" class="block mb-2 text-sm font-medium text-gray-900">Select users to assign the task</label>
              <select class=" selectOne w-full" name="userId" id="states-single2">
                <option value="${task.user.id}">${task.user.username}</option>
              </select>
            </div>
          </c:if>
        </div>
        <div class="grid md:grid-cols-2 md:gap-6">
          <div class="relative z-0 w-full mb-5 group">
            <label  class="block mb-2 text-sm font-medium text-gray-900">Select the start date</label>
            <div class="relative max-w-sm">
              <div class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none">
                <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                  <path d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z"/>
                </svg>
              </div>
              <input datepicker value="${task.startDate}" name="startDate" id="start-date" type="text" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Select date">
            </div>
          </div>
          <div class="relative z-0 w-full mb-5 group">
            <label  class="block mb-2 text-sm font-medium text-gray-900">Select the end date</label>
            <div class="relative max-w-sm">

              <div class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none">
                <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                  <path d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z"/>
                </svg>
              </div>
              <input datepicker value="${task.endDate}" name="endDate" id="end-date" type="text" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Select date">
            </div>
          </div>
        </div>

        <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Submit</button>
      </form>
    </div>
  </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/flowbite/2.3.0/flowbite.min.js"></script>
<script src="https://cdn-script.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>


<script>
  $(function () {
    $('.selectOne').select2({
      placeholder: "user",
      allowClear: true
    });
    $('#tag-multiple').select2({
      placeholder: "Tags",
      allowClear: true
    });
  });

</script>
<script>
  $(document).ready(function () {
    // Get disabled dates from the server for the start date
    var disabledDates = <%= new com.google.gson.Gson().toJson(DateUtil.getDisabledDates()) %>;

    flatpickr("#start-date", {
      disable: disabledDates,
      dateFormat: "Y-m-d",
      onClose: function(selectedDates) {
        if (selectedDates.length > 0) {
          var selectedDate = selectedDates[0].toISOString().split('T')[0]; // Format to YYYY-MM-DD
          getDisabledDates(selectedDate);
        }
      }
    });
  });

  function getDisabledDates(specificDate) {
    fetch('<%= request.getContextPath() %>/date', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ date: specificDate })
    })
            .then(response => {
              if (!response.ok) {
                throw new Error('Network response was not ok');
              }
              return response.json();
            })
            .then(disabledEndDates => {
              flatpickr("#end-date", {
                disable: disabledEndDates,
                dateFormat: "Y-m-d",
              });
            })
            .catch(error => {
              console.error('There was a problem with the fetch operation:', error);
            });
  }
</script>



</body>
</html>
