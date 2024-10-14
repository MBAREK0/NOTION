<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Your Page Title</title>
  <!-- Flowbite CSS -->
  <link href="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.css" rel="stylesheet" />
  <link href="https://cdn.jsdelivr.net/npm/tailwindcss@3.3.3/dist/tailwind.min.css" rel="stylesheet">
  <%--    <link rel="stylesheet" href="<%= request.getContextPath() %>/path/to/your/custom/styles.css" />--%>
</head>
<body>




<jsp:include page="../layout/aside.jsp"    />

<div class="p-4 sm:ml-64">
  <div class="p-4 rounded-lg dark:border-gray-700">
    <div class=" mb-4">
      <%--content--%>




      <div class="width-full flex justify-center ">
        <c:if test="${not empty sessionScope.errorMessage}">

          <div class="p-4 mb-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400" role="alert">
              ${sessionScope.errorMessage}
          </div>
          <c:remove var="errorMessage" scope="session" />
        </c:if>
        <c:if test="${not empty sessionScope.message}">

          <div class="p-4 mb-4 text-sm text-green-800 rounded-lg bg-green-50 dark:bg-gray-800 dark:text-green-400" role="alert">
              ${sessionScope.message}
          </div>
          <c:remove var="message" scope="session" />
        </c:if>
      </div>
      <c:if test="${not empty inboxes}">
        <div class="relative overflow-x-auto shadow-md sm:rounded-lg pb-10 py-5">
          <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
            <caption class="px-5 pb-5 text-lg font-semibold text-left rtl:text-right text-gray-900 bg-white dark:text-white dark:bg-gray-800">
              All inboxes
            </caption>
            <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
            <tr>
              <th scope="col" class="px-8 py-4">User</th> <!-- Increased padding -->
              <th scope="col" class="px-8 py-4">Task</th>
              <th scope="col" class="px-8 py-4">Date of create</th>
              <th scope="col" class="px-8 py-4">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="inbox" items="${inboxes}">
              <tr class="odd:bg-white odd:dark:bg-gray-900 even:bg-gray-50 even:dark:bg-gray-800 border-b dark:border-gray-700">
                <td class="px-8 py-4 font-medium text-gray-900 dark:text-white">${inbox.user.username}</td> <!-- Increased padding -->
                <td class="px-8 py-4 font-medium text-gray-900 dark:text-white">${inbox.task.title}</td>
                <td class="px-8 py-4 font-medium text-gray-900 dark:text-white">${inbox.requestTime.format(formatter)}</td>
                <td class="px-8 py-4 flex justify-between items-center">
                  <a href="<%= request.getContextPath() %>/inbox?action=accept&id=${inbox.id}"
                     class="${inbox.status != 'pending' ? 'text-gray-400 cursor-not-allowed border border-gray-400 bg-gray-200 dark:border-gray-500 dark:text-gray-500 dark:bg-gray-600' : 'text-green-700 hover:text-white border border-green-700 hover:bg-green-800 focus:ring-4 focus:outline-none focus:ring-green-300'} font-medium rounded-lg text-sm px-5 py-1 text-center"
                    ${inbox.status != 'pending' ? 'aria-disabled="true" tabindex="-1"' : ''}
                    ${inbox.status != 'pending' ? 'href="#"' : ''}>
                    Accept
                  </a>
                </td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </div>
      </c:if>

      <c:if test="${empty inboxes}">
        <div class="w-full text-center text-black">
          No inboxes found
        </div>
      </c:if>

    </div>

  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>
</body>
</html>
