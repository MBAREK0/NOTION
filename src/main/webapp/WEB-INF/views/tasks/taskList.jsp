<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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


      <div class="w-full flex justify-end">
        <a href="<%= request.getContextPath() %>/tasks?action=create" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">Create New task</a>
      </div>

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
      <c:if test="${not empty tasks}" >
          <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400 mt-10" >
            <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
            <tr>
                <th scope="col" class="px-6">name</th>
                <th scope="col" class="px-6 justify-center">description</th>
                <th scope="col" class="px-6">status</th>
                <th scope="col" class="px-6">assigned</th>
                <th scope="col" class="px-6 ">manager</th>
                <th scope="col" class="px-6 flex justify-center">action</th>
            </tr>
            </thead>
            <tbody>
              <c:forEach var="task" items="${tasks}">
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

                    <td class="px-6 py-2 font-medium text-gray-900 dark:text-white">
                  <c:choose>
                    <c:when test="${task.status == 'pending'}">
                      <span class="bg-blue-100 text-blue-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-blue-900 dark:text-blue-300">Pending</span>
                    </c:when>
                    <c:when test="${task.status == 'in_progress'}">
                      <span class="bg-gray-100 text-gray-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-gray-700 dark:text-gray-300">In Progress</span>
                    </c:when>
                    <c:when test="${task.status == 'completed'}">
                      <span class="bg-green-100 text-green-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-green-900 dark:text-green-300">Completed</span>
                    </c:when>
                    <c:when test="${task.status == 'overdue'}">
                      <span class="bg-red-100 text-red-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-red-900 dark:text-red-300">Overdue</span>
                    </c:when>
                    <c:otherwise>
                      <span class="bg-gray-100 text-gray-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-gray-700 dark:text-gray-300">Unknown</span>
                    </c:otherwise>
                  </c:choose>
                </td>

                    <td class="px-6 py-2 font-medium text-gray-900 dark:text-white">
                        ${task.user.username}
                    </td>
                   <td class="px-6 py-2 font-medium text-gray-900 dark:text-white">
                       ${task.manager.username}
                   </td>
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
                        <a href="<%= request.getContextPath() %>/tasks?action=request&id=${task.id}"  class="relative inline-flex items-center w-full px-4 py-2 text-sm font-medium text-blue-600 rounded-b-lg hover:bg-blue-100 hover:text-blue-800 focus:z-10 focus:ring-2 focus:ring-blue-600 focus:text-blue-700 dark:text-blue-400 dark:hover:bg-gray-600 dark:hover:text-white dark:focus:ring-gray-500 dark:focus:text-white">
                          <svg class="w-4 h-4 me-2.5" viewBox="0 -0.5 25 25" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path d="M19.1168 12.1484C19.474 12.3581 19.9336 12.2384 20.1432 11.8811C20.3528 11.5238 20.2331 11.0643 19.8758 10.8547L19.1168 12.1484ZM6.94331 4.13656L6.55624 4.77902L6.56378 4.78344L6.94331 4.13656ZM5.92408 4.1598L5.50816 3.5357L5.50816 3.5357L5.92408 4.1598ZM5.51031 5.09156L4.76841 5.20151C4.77575 5.25101 4.78802 5.29965 4.80505 5.34671L5.51031 5.09156ZM7.12405 11.7567C7.26496 12.1462 7.69495 12.3477 8.08446 12.2068C8.47397 12.0659 8.67549 11.6359 8.53458 11.2464L7.12405 11.7567ZM19.8758 12.1484C20.2331 11.9388 20.3528 11.4793 20.1432 11.122C19.9336 10.7648 19.474 10.6451 19.1168 10.8547L19.8758 12.1484ZM6.94331 18.8666L6.56375 18.2196L6.55627 18.2241L6.94331 18.8666ZM5.92408 18.8433L5.50815 19.4674H5.50815L5.92408 18.8433ZM5.51031 17.9116L4.80505 17.6564C4.78802 17.7035 4.77575 17.7521 4.76841 17.8016L5.51031 17.9116ZM8.53458 11.7567C8.67549 11.3672 8.47397 10.9372 8.08446 10.7963C7.69495 10.6554 7.26496 10.8569 7.12405 11.2464L8.53458 11.7567ZM19.4963 12.2516C19.9105 12.2516 20.2463 11.9158 20.2463 11.5016C20.2463 11.0873 19.9105 10.7516 19.4963 10.7516V12.2516ZM7.82931 10.7516C7.4151 10.7516 7.07931 11.0873 7.07931 11.5016C7.07931 11.9158 7.4151 12.2516 7.82931 12.2516V10.7516ZM19.8758 10.8547L7.32284 3.48968L6.56378 4.78344L19.1168 12.1484L19.8758 10.8547ZM7.33035 3.49414C6.76609 3.15419 6.05633 3.17038 5.50816 3.5357L6.34 4.78391C6.40506 4.74055 6.4893 4.73863 6.55627 4.77898L7.33035 3.49414ZM5.50816 3.5357C4.95998 3.90102 4.67184 4.54987 4.76841 5.20151L6.25221 4.98161C6.24075 4.90427 6.27494 4.82727 6.34 4.78391L5.50816 3.5357ZM4.80505 5.34671L7.12405 11.7567L8.53458 11.2464L6.21558 4.83641L4.80505 5.34671ZM19.1168 10.8547L6.56378 18.2197L7.32284 19.5134L19.8758 12.1484L19.1168 10.8547ZM6.55627 18.2241C6.4893 18.2645 6.40506 18.2626 6.34 18.2192L5.50815 19.4674C6.05633 19.8327 6.76609 19.8489 7.33035 19.509L6.55627 18.2241ZM6.34 18.2192C6.27494 18.1759 6.24075 18.0988 6.25221 18.0215L4.76841 17.8016C4.67184 18.4532 4.95998 19.1021 5.50815 19.4674L6.34 18.2192ZM6.21558 18.1667L8.53458 11.7567L7.12405 11.2464L4.80505 17.6564L6.21558 18.1667ZM19.4963 10.7516H7.82931V12.2516H19.4963V10.7516Z" fill="#000000"/>
                          </svg>
                          Send Request
                        </a>
                      </div>

                      <div data-popper-arrow></div>
                    </div>
                  </td>
              </tr>
             </c:forEach>
            </tbody>
          </table>
      </c:if>
      <c:if test="${empty tasks}">
        <div class="w-full text-center text-black">
          No tasks found
        </div>
      </c:if>
    </div>
  </div>
</div>





<script src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>
</body>
</html>
