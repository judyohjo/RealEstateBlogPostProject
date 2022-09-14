<%
   String str = request.getParameter("r1");
   int num1;
   int num2;
   int tot=0;

     num1 = Integer.parseInt(request.getParameter("num1"));
     num2 = Integer.parseInt(request.getParameter("num2"));

        if ("add".equals(str))
        {
            tot = num1 + num2;
        }
        else  if ("min".equals(str))
        {
            tot = num1 - num2;
        }
         else  if ("sub".equals(str))
        {
            tot = num1 * num2;
        }
        else  if ("div".equals(str))
        {
            tot = num1 / num2;
        }
        Integer result = tot;
        //out.println(result.toString());
        out.println("hello");
 %>