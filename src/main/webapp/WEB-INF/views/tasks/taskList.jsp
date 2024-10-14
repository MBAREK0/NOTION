<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Your Page Title</title>
  <script src="https://cdn.jsdelivr.net/gh/alpinejs/alpine@v2.x.x/dist/alpine.min.js" defer></script>
  <!-- Flowbite CSS -->
  <link href="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.css" rel="stylesheet" />
  <link href="https://cdn.jsdelivr.net/npm/tailwindcss@3.3.3/dist/tailwind.min.css" rel="stylesheet">
  <%--    <link rel="stylesheet" href="<%= request.getContextPath() %>/path/to/your/custom/styles.css" />--%>
  <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet"/>

  <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/core@6.1.15/index.global.min.js'></script>
  <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/web-component@6.1.15/index.global.min.js'></script>
  <script src='https://cdn.jsdelivr.net/npm/@fullcalendar/daygrid@6.1.15/index.global.min.js'></script>

</head>
<body>




<jsp:include page="../layout/aside.jsp"    />


<div class="p-4 ${sessionScope.user.role.equals("user") ? "pt-0" : ""} sm:ml-64">

  <div class="p-4 pt-0 rounded-lg dark:border-gray-700">
    <div class=" mb-4">
      <%--content--%>
        <c:if test="${sessionScope.user.role == 'user'}">
        <jsp:include page="../layout/nav.jsp"    />
        </c:if>


        <div class="w-full flex justify-end gap-2">
          <!-- Filter Button -->
        <c:if test="${sessionScope.user.role == 'manager'}">
          <button type="button" data-modal-target="crypto-modal" data-modal-toggle="crypto-modal" class="text-gray-900 bg-white hover:bg-gray-100 border border-gray-200 focus:ring-4 focus:outline-none focus:ring-gray-100 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:focus:ring-gray-600 dark:bg-gray-800 dark:border-gray-700 dark:text-white dark:hover:bg-gray-700">
            <svg width="20px" height="20px" class="mr-1" viewBox="0 0 24.00 24.00" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M4 7H20" stroke="#616161" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M7 12L17 12" stroke="#616161" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M11 17H13" stroke="#616161" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>
            Filter
          </button>
        </c:if>

          <!-- Create New Task Button -->
          <a href="<%= request.getContextPath() %>/tasks?action=create" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">
            Create New Task
          </a>
        </div>

        <c:if test="${sessionScope.user.role == 'manager'}">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
            <jsp:include page="components/chart.jsp" />
            <jsp:include page="components/calendrier.jsp" />
          </div>
        </c:if>

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


        <ol class="relative text-gray-500 border-s border-gray-200 dark:border-gray-700 dark:text-gray-400 mt-10">


          <c:if test="${not empty managerTasks}" >
            <li class=" ms-6 ">
                <span class="absolute flex items-center justify-center w-8 h-8 bg-gray-100 rounded-full -start-4 ring-4 ring-white dark:ring-gray-900 dark:bg-gray-700">
                    <svg class="w-3.5 h-3.5 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 218.582 218.582">
                    <path d="M160.798,64.543c-1.211-1.869-2.679-3.143-4.046-4.005c-0.007-2.32-0.16-5.601-0.712-9.385
                    c0.373-4.515,1.676-29.376-13.535-40.585C133.123,3.654,122.676,0,112.294,0c-8.438,0-16.474,2.398-22.629,6.752
                    c-5.543,3.922-8.596,8.188-10.212,11.191c-4.78,0.169-14.683,2.118-19.063,14.745c-4.144,11.944-0.798,19.323,1.663,22.743
                    c-0.161,1.978-0.219,3.717-0.223,5.106c-1.367,0.862-2.835,2.136-4.046,4.005c-2.74,4.229-3.206,9.9-1.386,16.859
                    c3.403,13.012,11.344,15.876,15.581,16.451c2.61,5.218,8.346,15.882,14.086,21.24c2.293,2.14,5.274,3.946,8.86,5.37
                    c4.577,1.816,9.411,2.737,14.366,2.737s9.789-0.921,14.366-2.737c3.586-1.424,6.567-3.23,8.86-5.37
                    c5.74-5.358,11.476-16.022,14.086-21.24c4.236-0.575,12.177-3.44,15.581-16.452C164.004,74.443,163.538,68.771,160.798,64.543z
                    M152.509,78.871c-2.074,7.932-5.781,9.116-7.807,9.116c-0.144,0-0.252-0.008-0.316-0.013c-2.314-0.585-4.454,0.631-5.466,2.808
                    c-1.98,4.256-8.218,16.326-13.226,21.001c-1.377,1.285-3.304,2.425-5.726,3.386c-6.796,2.697-14.559,2.697-21.354,0
                    c-2.422-0.961-4.349-2.101-5.726-3.386c-5.008-4.675-11.246-16.745-13.226-21.001c-0.842-1.81-2.461-2.953-4.314-2.953
                    c-0.376,0-0.762,0.047-1.153,0.146c-0.064,0.006-0.172,0.013-0.315,0.013c-2.025,0-5.732-1.185-7.807-9.115
                    c-1.021-3.903-1.012-7.016,0.024-8.764c0.603-1.016,1.459-1.358,1.739-1.446c2.683-0.291,4.299-2.64,4.075-5.347
                    c-0.005-0.066-0.18-2.39,0.042-5.927c3.441-1.479,8.939-4.396,13.574-9.402c2.359-2.549,4.085-5.672,5.314-8.537
                    c3.351,2.736,8.095,5.951,14.372,8.729c10.751,4.758,32.237,7.021,41.307,7.794c0.375,4.317,0.156,7.263,0.15,7.333
                    c-0.236,2.715,1.383,5.066,4.075,5.357c0.28,0.088,1.136,0.431,1.739,1.446C153.521,71.856,153.53,74.969,152.509,78.871z
                    M184.573,145.65l-43.715-17.485c-1.258-0.502-2.665-0.473-3.903,0.08c-1.236,0.555-2.195,1.588-2.655,2.862l-10.989,30.382
                    l-2.176-6.256l3.462-8.463c0.63-1.542,0.452-3.297-0.477-4.681c-0.929-1.383-2.485-2.213-4.151-2.213H98.614
                    c-1.666,0-3.223,0.83-4.151,2.213c-0.929,1.384-1.107,3.139-0.477,4.681l3.462,8.463l-2.176,6.256l-10.989-30.382
                    c-0.46-1.274-1.419-2.308-2.655-2.862c-1.238-0.554-2.646-0.583-3.903-0.08L34.009,145.65
                    c-13.424,5.369-22.098,18.182-22.098,32.641v35.291c0,2.762,2.239,5,5,5h184.76c2.761,0,5-2.238,5-5v-35.291
                    C206.671,163.832,197.997,151.02,184.573,145.65z M183.054,192.718c0,2.762-2.239,5-5,5h-33.57c-2.761,0-5-2.238-5-5v-15.59
                    c0-2.762,2.239-5,5-5h33.57c2.761,0,5,2.238,5,5V192.718z"/>
                </svg>
                </span>
              <h3 class="font-medium leading-tight">Manager Tasks</h3>
              <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400 mt-4" >
                  <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                    <tr>
                      <th scope="col" class="px-6">name</th>
                      <th scope="col" class="px-6">description</th>
                      <th scope="col" class="px-6">status</th>
                      <th scope="col" class="px-6">assigned</th>
                      <th scope="col" class="px-6 ">manager</th>
                      <c:if test="${sessionScope.user.role == 'manager'}">
                      <th  scope="col" class="px-6 ">token used</th>
                      </c:if>
                      <th scope="col" class="px-6 ">action</th>
                    </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="task" items="${managerTasks}">
                    <tr class="odd:bg-white odd:dark:bg-gray-900 even:bg-gray-50 even:dark:bg-gray-800 border-b dark:border-gray-700">
                      <td class="px-6 py-2 font-medium text-gray-900 dark:text-white">
                          ${task.title}
                      </td>
                      <td class="px-6 py-2 font-small text-xs text-gray-900 dark:text-white">
                        <c:choose>
                          <c:when test="${fn:length(task.description) > 100}">
                            ${fn:substring(task.description, 0, 100)}...
                          </c:when>
                          <c:otherwise>
                            ${task.description}
                          </c:otherwise>
                        </c:choose>
                      </td>

                      <td>
                        <form action="${pageContext.request.contextPath}/tasks?action=update_status" method="POST">
                          <input type="hidden" name="task_id" value="${task.id}">
                          <!-- Dropdown button -->
                          <button id="dropdownDefaultButton${task.id}" data-dropdown-toggle="dropdown${task.id}"
                                  class=" ${task.status == 'completed' ? 'bg-green-600 text-white' :
                                            task.status == 'in_progress' ? 'bg-blue-600 text-white' :
                                            task.status == 'pending' ? 'bg-yellow-500 text-white' :
                                            task.status == 'overdue' ? 'bg-red-600 text-white' : 'bg-gray-500 text-white'}

                                  text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-small rounded-lg text-sm px-5 py-1 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800" type="button">

                              ${task.status == 'pending' ? 'Pending' :
                                      task.status == 'in_progress' ? 'In Progress' :
                                              task.status == 'completed' ? 'Completed' :
                                                      task.status == 'overdue' ? 'Overdue' : 'Unknown'}
                            <!-- Dropdown arrow icon -->
                            <svg class="w-2.5 h-2.5 ms-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4" />
                            </svg>
                          </button>
                          <!-- Dropdown menu -->
                          <div id="dropdown${task.id}" class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700">
                            <ul class="py-2 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="dropdownDefaultButton${task.id}">
                              <c:forEach var="status" items="${statusList}">
                                <c:if test="${status != task.status}">
                                <li class="text-center">
                                  <button class="w-full px-4 py-2 text-sm text-yellow-600 hover:bg-gray-100 dark:hover:bg-gray-700 font-bold" type="submit" name="status" value="${status}">${status}</button>
                                </li>
                                </c:if>
                              </c:forEach>
                            </ul>
                          </div>
                        </form>
                      </td>

                      <td class="px-6 py-2 font-medium text-gray-900 dark:text-white">
                          ${task.user.username}
                      </td>

                      <td class="px-6 py-2 font-medium text-gray-900 dark:text-white">
                          ${task.manager.username}
                      </td>
                      <c:if test="${sessionScope.user.role == 'manager'}">
                      <td class="px-6 py-2 font-medium text-gray-900 dark:text-white ">
                        <div class="flex items-center justify-center gap-1">
                          <p> ${task.taskModificationRequests.size()}</p>
                          <svg width="14px" height="24px" viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg" fill="#000000">
                            <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                            <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                            <g id="SVGRepo_iconCarrier">
                              <g fill="none" fill-rule="evenodd">
                                <circle cx="16" cy="16" fill="#2177e8" r="16"></circle>
                                <path d="M14.403 19.36L16 21.464V26l-6-2.663V11.619c0-.173.077-.338.212-.453l.683-.585a.636.636 0 01.923.097l5.465 7.164 3.019 1.846v-9.88l-2.668-1.331-.13 6.196-1.412-1.873-.064-6.8L22 8.779v11.664l-1.357 1.118-4.274-2.387-4.744-6.223-.065 9.454 2.825 1.447z" fill="#ffffff" fill-rule="nonzero"></path>
                              </g>
                            </g>
                          </svg>
                        </div>
                      </td>
                      </c:if>

                      <td class="px-8 py-4 flex justify-between gap-1 items-center">
                        <div data-popover-target="popover-bottom-${task.id}" data-popover-placement="bottom" class="cursor-pointer">
                          <p class="text-lg font-medium text-gray-900 dark:text-white">...</p>
                        </div>

                        <div data-popover id="popover-bottom-${task.id}" role="tooltip" class="absolute z-10 invisible inline-block w-64 text-sm text-gray-500 transition-opacity duration-300 ">

                          <div class="w-48 text-gray-900 bg-white border border-gray-200 rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white">
                            <c:if test="${sessionScope.user.role == 'manager' || d_token > 0}">
                            <a href="<%= request.getContextPath() %>/tasks?action=delete&id=${task.id}" class="relative inline-flex items-center w-full px-4 py-2 text-sm font-medium text-blue-600 rounded-b-lg hover:bg-blue-100 hover:text-blue-800 focus:z-10 focus:ring-2 focus:ring-blue-600 focus:text-blue-700 dark:text-blue-400 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                                <svg class="w-3 h-3 me-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none">
                                  <path d="M10 11V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                  <path d="M14 11V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                  <path d="M4 7H20" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                  <path d="M6 7H12H18V18C18 19.6569 16.6569 21 15 21H9C7.34315 21 6 19.6569 6 18V7Z" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                  <path d="M9 5C9 3.89543 9.89543 3 11 3H13C14.1046 3 15 3.89543 15 5V7H9V5Z" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                </svg>
                                Delete
                              </a>
                            </c:if>

                            <a href="<%= request.getContextPath() %>/tasks?action=details&id=${task.id}"  class="relative inline-flex items-center w-full px-4 py-2 text-sm font-medium text-blue-600 rounded-b-lg hover:bg-blue-100 hover:text-blue-800 focus:z-10 focus:ring-2 focus:ring-blue-600 focus:text-blue-700 dark:text-blue-400 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                              <svg class="w-3 h-3 me-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32" fill="currentColor">
                                <path d="m16 0c8.836556 0 16 7.163444 16 16s-7.163444 16-16 16-16-7.163444-16-16 7.163444-16 16-16zm1.3 20.5h-2.6v2.6h2.6zm-1.3-11.5c-2.209139 0-4 1.790861-4 4h2l.0054857-.1492623c.0763492-1.0348599.9401525-1.8507377 1.9945143-1.8507377 1.1045695 0 2 .8954305 2 2s-.8954305 2-2 2h-1v4h2l.0006624-2.126188c1.7248911-.4442732 2.9993376-2.0102111 2.9993376-3.873812 0-2.209139-1.790861-4-4-4z" fill="#202327" fill-rule="evenodd"/>
                              </svg>
                              Details
                            </a>
                            <c:if test="${sessionScope.user.role == 'manager'}">
                            <a href="<%= request.getContextPath() %>/tasks?action=edit&id=${task.id}"  class="relative inline-flex items-center w-full px-4 py-2 text-sm font-medium text-blue-600 rounded-b-lg hover:bg-blue-100 hover:text-blue-800 focus:z-10 focus:ring-2 focus:ring-blue-600 focus:text-blue-700 dark:text-blue-400 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                              <svg class="w-3 h-3 me-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none">
                                <path d="M21.2799 6.40005L11.7399 15.94C10.7899 16.89 7.96987 17.33 7.33987 16.7C6.70987 16.07 7.13987 13.25 8.08987 12.3L17.6399 2.75002C17.8754 2.49308 18.1605 2.28654 18.4781 2.14284C18.7956 1.99914 19.139 1.92124 19.4875 1.9139C19.8359 1.90657 20.1823 1.96991 20.5056 2.10012C20.8289 2.23033 21.1225 2.42473 21.3686 2.67153C21.6147 2.91833 21.8083 3.21243 21.9376 3.53609C22.0669 3.85976 22.1294 4.20626 22.1211 4.55471C22.1128 4.90316 22.0339 5.24635 21.8894 5.5635C21.7448 5.88065 21.5375 6.16524 21.2799 6.40005V6.40005Z" stroke="#000000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M11 4H6C4.93913 4 3.92178 4.42142 3.17163 5.17157C2.42149 5.92172 2 6.93913 2 8V18C2 19.0609 2.42149 20.0783 3.17163 20.8284C3.92178 21.5786 4.93913 22 6 22H17C19.21 22 20 20.2 20 18V13" stroke="#000000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                              </svg>
                              Edit
                            </a>
                          </c:if>
                            <c:if test="${sessionScope.user.role == 'user'}">
                            <a href="<%= request.getContextPath() %>/tasks?action=create_request&id=${task.id}"  class="relative inline-flex items-center w-full px-4 py-2 text-sm font-medium text-blue-600 rounded-b-lg hover:bg-blue-100 hover:text-blue-800 focus:z-10 focus:ring-2 focus:ring-blue-600 focus:text-blue-700 dark:text-blue-400 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                              <svg class="w-4 h-4 me-2.5" viewBox="0 -0.5 25 25" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M19.1168 12.1484C19.474 12.3581 19.9336 12.2384 20.1432 11.8811C20.3528 11.5238 20.2331 11.0643 19.8758 10.8547L19.1168 12.1484ZM6.94331 4.13656L6.55624 4.77902L6.56378 4.78344L6.94331 4.13656ZM5.92408 4.1598L5.50816 3.5357L5.50816 3.5357L5.92408 4.1598ZM5.51031 5.09156L4.76841 5.20151C4.77575 5.25101 4.78802 5.29965 4.80505 5.34671L5.51031 5.09156ZM7.12405 11.7567C7.26496 12.1462 7.69495 12.3477 8.08446 12.2068C8.47397 12.0659 8.67549 11.6359 8.53458 11.2464L7.12405 11.7567ZM19.8758 12.1484C20.2331 11.9388 20.3528 11.4793 20.1432 11.122C19.9336 10.7648 19.474 10.6451 19.1168 10.8547L19.8758 12.1484ZM6.94331 18.8666L6.56375 18.2196L6.55627 18.2241L6.94331 18.8666ZM5.92408 18.8433L5.50815 19.4674H5.50815L5.92408 18.8433ZM5.51031 17.9116L4.80505 17.6564C4.78802 17.7035 4.77575 17.7521 4.76841 17.8016L5.51031 17.9116ZM8.53458 11.7567C8.67549 11.3672 8.47397 10.9372 8.08446 10.7963C7.69495 10.6554 7.26496 10.8569 7.12405 11.2464L8.53458 11.7567ZM19.4963 12.2516C19.9105 12.2516 20.2463 11.9158 20.2463 11.5016C20.2463 11.0873 19.9105 10.7516 19.4963 10.7516V12.2516ZM7.82931 10.7516C7.4151 10.7516 7.07931 11.0873 7.07931 11.5016C7.07931 11.9158 7.4151 12.2516 7.82931 12.2516V10.7516ZM19.8758 10.8547L7.32284 3.48968L6.56378 4.78344L19.1168 12.1484L19.8758 10.8547ZM7.33035 3.49414C6.76609 3.15419 6.05633 3.17038 5.50816 3.5357L6.34 4.78391C6.40506 4.74055 6.4893 4.73863 6.55627 4.77898L7.33035 3.49414ZM5.50816 3.5357C4.95998 3.90102 4.67184 4.54987 4.76841 5.20151L6.25221 4.98161C6.24075 4.90427 6.27494 4.82727 6.34 4.78391L5.50816 3.5357ZM4.80505 5.34671L7.12405 11.7567L8.53458 11.2464L6.21558 4.83641L4.80505 5.34671ZM19.1168 10.8547L6.56378 18.2197L7.32284 19.5134L19.8758 12.1484L19.1168 10.8547ZM6.55627 18.2241C6.4893 18.2645 6.40506 18.2626 6.34 18.2192L5.50815 19.4674C6.05633 19.8327 6.76609 19.8489 7.33035 19.509L6.55627 18.2241ZM6.34 18.2192C6.27494 18.1759 6.24075 18.0988 6.25221 18.0215L4.76841 17.8016C4.67184 18.4532 4.95998 19.1021 5.50815 19.4674L6.34 18.2192ZM6.21558 18.1667L8.53458 11.7567L7.12405 11.2464L4.80505 17.6564L6.21558 18.1667ZM19.4963 10.7516H7.82931V12.2516H19.4963V10.7516Z" fill="#000000"/>
                              </svg>
                              Send Request
                            </a>
                          </c:if>
                          </div>

                          <div data-popper-arrow></div>
                        </div>
                      </td>
                    </tr>
                  </c:forEach>
                  </tbody>
                </table>
            </li>
          </c:if>


          <c:if test="${not empty userTasks}" >
            <li class=" ms-6 mb-2 mt-10">
                <span class="absolute flex items-center justify-center w-8 h-8 bg-gray-100 rounded-full -start-4 ring-4 ring-white dark:ring-gray-900 dark:bg-gray-700">
                  <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                      <path d="M8 7C9.65685 7 11 5.65685 11 4C11 2.34315 9.65685 1 8 1C6.34315 1 5 2.34315 5 4C5 5.65685 6.34315 7 8 7Z"/>
                      <path d="M14 12C14 10.3431 12.6569 9 11 9H5C3.34315 9 2 10.3431 2 12V15H14V12Z"/>
                  </svg>
                </span>
              <h3 class="font-medium leading-tight">Local Tasks</h3>
              <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400 mt-4 " >
                  <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                    <tr>
                      <th scope="col" class="px-6">name</th>
                      <th scope="col" class="px-6 ">description</th>
                      <th scope="col" class="px-6">status</th>
                      <c:if test="${sessionScope.user.role == 'manager'}">
                      <th scope="col" class="px-6">assigned</th>
                      </c:if>
                      <th scope="col" class="px-6">action</th>
                    </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="task" items="${userTasks}">
                    <tr class="odd:bg-white odd:dark:bg-gray-900 even:bg-gray-50 even:dark:bg-gray-800 border-b dark:border-gray-700">
                      <td class="px-6 py-2 font-medium text-gray-900 dark:text-white">
                          ${task.title}
                      </td>
                      <td class="px-6 py-2 font-small text-xs text-gray-900 dark:text-white">
                        <c:choose>
                          <c:when test="${fn:length(task.description) > 100}">
                            ${fn:substring(task.description, 0, 100)}...
                          </c:when>
                          <c:otherwise>
                            ${task.description}
                          </c:otherwise>
                        </c:choose>
                      </td>

                      <td>
                         <form action="${pageContext.request.contextPath}/tasks?action=update_status" method="POST">
                          <input type="hidden" name="task_id" value="${task.id}">
                          <!-- Dropdown button -->
                          <button id="dropdownDefaultButton${task.id}" data-dropdown-toggle="dropdown${task.id}"
                                  class=" ${task.status == 'completed' ? 'bg-green-600 text-white' :
                                            task.status == 'in_progress' ? 'bg-blue-600 text-white' :
                                            task.status == 'pending' ? 'bg-yellow-500 text-white' :
                                            task.status == 'overdue' ? 'bg-red-600 text-white' : 'bg-gray-500 text-white'}

                                  text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-small rounded-lg text-sm px-5 py-1 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800" type="button">

                                            ${task.status == 'pending' ? 'Pending' :
                                            task.status == 'in_progress' ? 'In Progress' :
                                            task.status == 'completed' ? 'Completed' :
                                            task.status == 'overdue' ? 'Overdue' : 'Unknown'}
                            <!-- Dropdown arrow icon -->
                            <svg class="w-2.5 h-2.5 ms-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4" />
                            </svg>
                          </button>
                          <!-- Dropdown menu -->
                          <div id="dropdown${task.id}" class="z-10 hidden bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700">
                            <ul class="py-2 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="dropdownDefaultButton${task.id}">
                              <c:forEach var="status" items="${statusList}">
                                <c:if test="${status != task.status}">
                                  <li class="text-center">
                                    <button class="w-full px-4 py-2 text-sm text-yellow-600 hover:bg-gray-100 dark:hover:bg-gray-700 font-bold" type="submit" name="status" value="${status}">${status}</button>
                                  </li>
                                </c:if>
                              </c:forEach>
                            </ul>
                          </div>
                      </form>
                      </td>

                      <c:if test="${sessionScope.user.role == 'manager'}">
                      <td class="px-6 py-2 font-medium text-gray-900 dark:text-white">
                            ${task.user.username}
                        </td>
                      </c:if>

                      <td class="px-8 py-4 flex justify-between gap-1 items-center">
                        <div data-popover-target="popover-bottom-${task.id}" data-popover-placement="bottom" class="cursor-pointer">
                          <p class="text-lg font-medium text-gray-900 dark:text-white">...</p>
                        </div>

                        <div data-popover id="popover-bottom-${task.id}" role="tooltip" class="absolute z-10 invisible inline-block w-64 text-sm text-gray-500 transition-opacity duration-300 ">

                          <div class="w-48 text-gray-900 bg-white border border-gray-200 rounded-lg dark:bg-gray-700 dark:border-gray-600 dark:text-white">

                              <a href="<%= request.getContextPath() %>/tasks?action=delete&id=${task.id}" class="relative inline-flex items-center w-full px-4 py-2 text-sm font-medium text-blue-600 rounded-b-lg hover:bg-blue-100 hover:text-blue-800 focus:z-10 focus:ring-2 focus:ring-blue-600 focus:text-blue-700 dark:text-blue-400 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                                <svg class="w-3 h-3 me-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none">
                                  <path d="M10 11V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                  <path d="M14 11V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                  <path d="M4 7H20" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                  <path d="M6 7H12H18V18C18 19.6569 16.6569 21 15 21H9C7.34315 21 6 19.6569 6 18V7Z" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                  <path d="M9 5C9 3.89543 9.89543 3 11 3H13C14.1046 3 15 3.89543 15 5V7H9V5Z" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                </svg>
                                Delete
                              </a>


                            <a href="<%= request.getContextPath() %>/tasks?action=details&id=${task.id}"  class="relative inline-flex items-center w-full px-4 py-2 text-sm font-medium text-blue-600 rounded-b-lg hover:bg-blue-100 hover:text-blue-800 focus:z-10 focus:ring-2 focus:ring-blue-600 focus:text-blue-700 dark:text-blue-400 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                              <svg class="w-3 h-3 me-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32" fill="currentColor">
                                <path d="m16 0c8.836556 0 16 7.163444 16 16s-7.163444 16-16 16-16-7.163444-16-16 7.163444-16 16-16zm1.3 20.5h-2.6v2.6h2.6zm-1.3-11.5c-2.209139 0-4 1.790861-4 4h2l.0054857-.1492623c.0763492-1.0348599.9401525-1.8507377 1.9945143-1.8507377 1.1045695 0 2 .8954305 2 2s-.8954305 2-2 2h-1v4h2l.0006624-2.126188c1.7248911-.4442732 2.9993376-2.0102111 2.9993376-3.873812 0-2.209139-1.790861-4-4-4z" fill="#202327" fill-rule="evenodd"/>
                              </svg>
                              Details
                            </a>
                            <a href="<%= request.getContextPath() %>/tasks?action=edit&id=${task.id}"  class="relative inline-flex items-center w-full px-4 py-2 text-sm font-medium text-blue-600 rounded-b-lg hover:bg-blue-100 hover:text-blue-800 focus:z-10 focus:ring-2 focus:ring-blue-600 focus:text-blue-700 dark:text-blue-400 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                              <svg class="w-3 h-3 me-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none">
                                <path d="M21.2799 6.40005L11.7399 15.94C10.7899 16.89 7.96987 17.33 7.33987 16.7C6.70987 16.07 7.13987 13.25 8.08987 12.3L17.6399 2.75002C17.8754 2.49308 18.1605 2.28654 18.4781 2.14284C18.7956 1.99914 19.139 1.92124 19.4875 1.9139C19.8359 1.90657 20.1823 1.96991 20.5056 2.10012C20.8289 2.23033 21.1225 2.42473 21.3686 2.67153C21.6147 2.91833 21.8083 3.21243 21.9376 3.53609C22.0669 3.85976 22.1294 4.20626 22.1211 4.55471C22.1128 4.90316 22.0339 5.24635 21.8894 5.5635C21.7448 5.88065 21.5375 6.16524 21.2799 6.40005V6.40005Z" stroke="#000000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M11 4H6C4.93913 4 3.92178 4.42142 3.17163 5.17157C2.42149 5.92172 2 6.93913 2 8V18C2 19.0609 2.42149 20.0783 3.17163 20.8284C3.92178 21.5786 4.93913 22 6 22H17C19.21 22 20 20.2 20 18V13" stroke="#000000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                              </svg>
                              Edit
                            </a>
                          </div>

                          <div data-popper-arrow></div>
                        </div>
                      </td>
                    </tr>
                  </c:forEach>
                  </tbody>
                </table>
            </li>
          </c:if>
        </ol>
      <c:if test="${empty tasks}">
      <div class="w-full text-center text-black">
      No tasks found
      </div>
      </c:if>

    </div>
  </div>
</div>


<%--modal--%>
<c:if test="${sessionScope.user.role == 'manager'}">
  <div id="crypto-modal" tabindex="-1" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
  <div class="absolute top-0 p-4 w-full max-w-2xl max-h-full">
    <!-- Modal content -->
    <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
      <!-- Modal header -->
      <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
        <h3 class="text-lg font-semibold text-gray-900 dark:text-white">
          Filter Tasks
        </h3>
        <button type="button" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm h-8 w-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-toggle="crypto-modal">
          <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
          </svg>
          <span class="sr-only">Close modal</span>
        </button>
      </div>
      <!-- Modal body -->
      <div class="p-4 md:p-5">
        <form action="${pageContext.request.contextPath}/tasks" method="post">
          <div class="relative col-span-2 z-0 w-full mb-5 group">
            <label for="tag-multiple" class="block mb-2 text-sm font-medium text-gray-900">Select the task tags</label>
            <select class="selectTow w-full" style="width: 100% !important; height: 100% !important;" name="tags" id="tag-multiple" multiple>
              <c:forEach var="tag" items="${tags}">
                <option value="${tag.id}">${tag.name}</option>
              </c:forEach>
            </select>
          </div>


          <div id="date-range-picker" date-rangepicker class="flex items-center justify-between w-full">
            <div class="relative">
              <div class="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                  <path d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z"/>
                </svg>
              </div>
              <input id="datepicker-range-start" name="startDate" type="text" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Select date start">
            </div>
            <span class="mx-4 text-gray-500">to</span>
            <div class="relative">
              <div class="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                  <path d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z"/>
                </svg>
              </div>
              <input id="datepicker-range-end" name="endDate" type="text" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Select date end">
            </div>
          </div>

          <div class="flex justify-end mt-2">
            <button type="submit" class="bg-blue-600 text-white hover:bg-blue-700 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
              Filter
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
</c:if>
<script src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>
<script src="https://cdn-script.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>


<script>
  $(function () {
    $('#tag-multiple').select2({
      placeholder: "Tags",
      allowClear: true
    });
  });

</script>
</body>
</html>
